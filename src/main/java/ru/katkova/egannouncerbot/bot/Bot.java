package ru.katkova.egannouncerbot.bot;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.katkova.egannouncerbot.ApplicationProperties;
import ru.katkova.egannouncerbot.data.Promotion;
import ru.katkova.egannouncerbot.data.User;
import ru.katkova.egannouncerbot.service.PromotionService;
import ru.katkova.egannouncerbot.service.UserService;

import java.text.SimpleDateFormat;
import java.util.List;

@Component
@EnableConfigurationProperties(ApplicationProperties.class)
public class Bot extends TelegramLongPollingBot {
    private final ApplicationProperties properties;
    @Autowired
    private UserService userService;
    @Autowired
    private PromotionService promotionService;

    private static final String GREETINGS_TEXT = "Привет! Я Epic Games Announcer Bot. \n"+
            "Я буду присылать тебе анонсы бесплатных игр, которые появляются в Epic Games Store каждую неделю. \n" +
            "Обычно новые бесплатные раздачи появляются в EGS по четвергам в 18-00, но на всякий случай я проверяю обновления каждый день. \n" +
            "Приятного пользования!";

    @Autowired
    public Bot(ApplicationProperties properties) {
        this.properties = properties;
    }

    @SneakyThrows
    public void onUpdateReceived(Update update) {
        User user = new User();
        Long chatId = update.getMessage().getChatId();
        user.setChatId(chatId);

        if (!userService.existsInDB(chatId)) {
            userService.createNewUser(user);

            SendMessage greetings = SendMessage.builder()
                    .chatId(chatId)
                    .text(GREETINGS_TEXT)
                    .build();
            execute(greetings);

            List<Promotion> promotionList = promotionService.getActualPromotions();
            for (Promotion p: promotionList) {
                formAndSendPromotion(p, user);
            }
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        if (updates.get(0).hasMyChatMember()) {
            ChatMemberUpdated chatMemberUpdated = updates.get(0).getMyChatMember();
            if (chatMemberUpdated.getNewChatMember().getStatus().equals("kicked")) {
                userService.deleteUser(chatMemberUpdated.getChat().getId());
            }
        } else updates.forEach(this::onUpdateReceived);
    }

    public void formAndSendPromotion(Promotion pr, User user) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String start = simpleDateFormat.format(pr.startDate);
        String end = simpleDateFormat.format(pr.endDate);
        String preparedText = "*Название:* "+pr.title + "\n" + "*Описание:* "+ pr.description + "\n" + "*Начало раздачи:* " + start + "\n" + "*Окончание раздачи:* " + end;
        if (pr.getImageUrl() != null) {
            InputFile inputFile = new InputFile(pr.getImageUrl());
            SendPhoto message = SendPhoto.builder()
                    .photo(inputFile)
                    .chatId(user.getChatId())
                    .parseMode("Markdown")
                    .caption(preparedText)
                    .build();
//            try {
//                execute(message);
//            }  catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
        } else {
            SendMessage message = SendMessage.builder()
                    .chatId(user.getChatId())
                    .parseMode("Markdown")
                    .text(preparedText).build();
//            try {
//                execute(message);
//            }  catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
        }
    }

    @Override
    public String getBotUsername() {
        return properties.getUsername();
    }

    @Override
    public String getBotToken() {
        return properties.getToken();
    }
}

package ru.katkova.egannouncerbot.bot;

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
import java.util.List;

@Component
@EnableConfigurationProperties(ApplicationProperties.class)
public class Bot extends TelegramLongPollingBot {
    private final ApplicationProperties properties;
    @Autowired
    private UserService userService;
    @Autowired
    private PromotionService promotionService;

    @Autowired
    public Bot(ApplicationProperties properties) {
        this.properties = properties;
    }

    public void onUpdateReceived(Update update) {
        User user = new User();
        Long chatId = update.getMessage().getChatId();
        user.setChatId(chatId);

        if (!userService.existsInDB(chatId)) {
            userService.createNewUser(user);

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
        String preparedText = "*Название:* "+pr.title + "\n" + "*Описание:* "+ pr.description + "\n" + "*Начало раздачи:* " + pr.startDate + "\n" + "*Окончание раздачи:* " + pr.endDate;
        if (pr.getImageUrl() != null) {
            InputFile inputFile = new InputFile(pr.getImageUrl());
            SendPhoto message = SendPhoto.builder()
                    .photo(inputFile)
                    .chatId(user.getChatId())
                    .parseMode("Markdown")
                    .caption(preparedText).build();
            try {
                execute(message);
            }  catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else {
            SendMessage message = SendMessage.builder()
                    .chatId(user.getChatId())
                    .parseMode("Markdown")
                    .text(preparedText).build();
            try {
                execute(message);
            }  catch (TelegramApiException e) {
                e.printStackTrace();
            }
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

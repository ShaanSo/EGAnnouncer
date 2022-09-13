package ru.katkova.egannouncerbot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.katkova.egannouncerbot.ApplicationProperties;
import ru.katkova.egannouncerbot.data.User;
import ru.katkova.egannouncerbot.service.EpicGamesService;
import ru.katkova.egannouncerbot.service.UserService;
import java.util.List;

@Component
@EnableConfigurationProperties(ApplicationProperties.class)
public class Bot extends TelegramLongPollingBot {

    private final ApplicationProperties properties;

    @Autowired
    private UserService userService;

    @Autowired
    private EpicGamesService epicGamesService;

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

// отправляем новому пользователю актуальные апдейты
//            String response = CheckForUpdatesJob.executeService(epicGamesService);
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonData jsonData;
//            List<Promotion> promotionList = new ArrayList<>();
//            jsonData = objectMapper.readValue(response, JsonData.class);
//            promotionList = jsonData.getCurrentPromotionsList();
        }

//        if (update.getChatMember().getOldChatMember().getStatus().equals("left")) {
//            System.out.println("user left");
//        }

        //        if (удаление пользователя)
//        {
//            удаляем из БД
//        }

//        if (!update.getMessage().getNewChatMembers().isEmpty()) {
//            SendMessage message = new SendMessage().setChatId(chatId).setText("rounded_text");
//            try {
//                execute(message);
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (update.getMessage().isUserMessage()) {
//            SendMessage message = new SendMessage().setChatId(chatId).setText("user_reply");
//            try {
//                execute(message);
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public String getBotUsername() {
        return properties.getUsername();
    }

    @Override
    public String getBotToken() {
        return properties.getToken();
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        if (updates.get(0).hasChatMember()) {
            ChatMemberUpdated chatMemberUpdated = updates.get(0).getChatMember();
            if (chatMemberUpdated.getNewChatMember().getStatus().equals("kicked")) {
//                реализовать удаление пользователя из БД
            }
        } else updates.forEach(this::onUpdateReceived);
    }
}

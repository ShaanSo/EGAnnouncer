package ru.katkova.egannouncerbot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.katkova.egannouncerbot.ApplicationProperties;
import ru.katkova.egannouncerbot.data.User;
import ru.katkova.egannouncerbot.service.UserService;

@Component
@EnableConfigurationProperties(ApplicationProperties.class)
public class Bot extends TelegramLongPollingBot {

    private final ApplicationProperties properties;

    @Autowired
    private UserService userService;

    @Autowired
    public Bot(ApplicationProperties properties) {
        this.properties = properties;
    }

    public void onUpdateReceived(Update update) {
        User user = new User();
        Long chatId = update.getMessage().getChatId();
        if (!userService.existsInDB(chatId)) {
            userService.createNewUser(user);
        }

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
}

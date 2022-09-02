package ru.katkova.egannouncerbot.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.katkova.egannouncerbot.ApplicationProperties;
import ru.katkova.egannouncerbot.data.User;
import ru.katkova.egannouncerbot.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {

    private final ApplicationProperties properties;

//    public static final String BOT_USER_NAME = "GameAnnouncerBot";
//    public static final String BOT_TOKEN = "5486943382:AAF1pb6Cs4pmQqIpQtFlVrP9y70jeXmU8OU";

    UserRepository connectionU = new UserRepository();

    public void onUpdateReceived(Update update) {
        User user = new User();
        update.getMessage().getChatId();
        Long chatId = update.getMessage().getChatId();
        connectionU.createNewUser(chatId);

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

//    @Override
//    public String getBotUsername() {
//        return BOT_USER_NAME;
//    }
//
//    @Override
//    public String getBotToken() {
//        return BOT_TOKEN;
//    }
}

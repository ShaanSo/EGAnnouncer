package ru.katkova.egannouncerbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.katkova.egannouncerbot.bot.Bot;

@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String[] args)
//            throws JsonProcessingException
    {

        ConfigurableApplicationContext appContext = SpringApplication.run(Application.class, args);

        try {
            Bot bot = appContext.getBean(Bot.class);
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

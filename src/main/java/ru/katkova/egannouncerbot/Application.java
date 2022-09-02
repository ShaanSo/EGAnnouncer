package ru.katkova.egannouncerbot;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.katkova.egannouncerbot.bot.Bot;
import ru.katkova.egannouncerbot.repository.PromotionRepository;
import ru.katkova.egannouncerbot.repository.UserRepository;

@SpringBootApplication
@EnableScheduling
@Component
@EnableConfigurationProperties(ApplicationProperties.class)
public class Application {

//    private static final Map<String, String> getenv = System.getenv();

    public static void main(String[] args) throws JsonProcessingException {

        ConfigurableApplicationContext appContext = SpringApplication.run(Application.class, args);

        UserRepository U = new UserRepository();
        U.createUsersTable();
        PromotionRepository P = new PromotionRepository();
        P.createPromotionsTable();

        try {
            Bot bot = new Bot(appContext.getBean(ApplicationProperties.class));
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

//        EpicGamesService epicGamesService = new EpicGamesService();
//        CheckForUpdatesJob checkForUpdatesJob = new CheckForUpdatesJob();
//        String response = checkForUpdatesJob.executeService();
    }
}

package ru.katkova.egannouncerbot.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.katkova.egannouncerbot.bot.Bot;
import ru.katkova.egannouncerbot.data.JsonData;
import ru.katkova.egannouncerbot.data.Promotion;
import ru.katkova.egannouncerbot.service.EpicGamesService;
import ru.katkova.egannouncerbot.data.User;
import ru.katkova.egannouncerbot.service.PromotionService;
import ru.katkova.egannouncerbot.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@EnableScheduling
@Slf4j
public class CheckForUpdatesJob {

    @Autowired
    EpicGamesService epicGamesService;

    @Autowired
    PromotionService promotionService;
    @Autowired
    UserService userService;
    @Autowired
    Bot bot;

    @Scheduled(cron = "${bot.scheduleCron}", zone = "${bot.timeZone}")
    @SneakyThrows
//    @Scheduled(fixedRate = 10000L)
    public void check() throws JsonProcessingException {

        //делаем запрос в API и разбираем ответ
        String response = executeService(epicGamesService);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonData jsonData;
        List<Promotion> promotionList = new ArrayList<>();
        jsonData = objectMapper.readValue(response, JsonData.class);
        promotionList = jsonData.getCurrentPromotionsList();
//       для теста
//        try {
//            File file = new File("src/main/resources/test2.json");
//            jsonData = objectMapper.readValue(file, JsonData.class);
//            promotionList = jsonData.getCurrentPromotionsList();
//        } catch (StreamReadException estr) {
//            System.out.println("StreamReadException");
//        } catch (IOException e) {
//            System.out.println("IOEXCEPTION");
//            e.printStackTrace();
//        }

        //получаем пользователей из БД, которым нужно разослать уведомление
        List<User> userList =  userService.restoreUsersFromDB();

        //проходим по списку присланных предложений
        for (Promotion pr: promotionList) {
            //проверяем, подходит ли предложение и не публиковали ли его уже
            if (promotionService.isActualPromotion(pr) && (!promotionService.existsInDB(pr))) {
                //складываем в БД
                pr.setId(UUID.randomUUID().toString());
                promotionService.putIntoDB(pr);
                //формируем текст сообщения
                for (User user:userList) {
                    InputFile inputFile = new InputFile(pr.getImageUrl());
                    String preparedText = "*Название:* "+pr.title + "\n" + "*Описание:* "+ pr.description + "\n" + "*Начало раздачи:* " + pr.startDate + "\n" + "*Окончание раздачи:* " + pr.endDate;
                    SendPhoto message = new SendPhoto();
                    message.setPhoto(inputFile);
                    message.setParseMode("Markdown");
                    message.setCaption(preparedText);
                    message.setChatId(user.getChatId());
                    try {
                        bot.execute(message);
                    }  catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @SneakyThrows
    public String executeService(EpicGamesService epicGamesService)
            throws JsonProcessingException, RestClientException {
        log.debug("Received [request '{}]' for ExecuteRiskService");
        var response = epicGamesService.handleRequest();
        log.debug("Send [response '{}]' for ExecuteRiskService");
        return response;
    }
}
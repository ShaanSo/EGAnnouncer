//package ru.katkova.egannouncerbot.scheduler;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//import ru.katkova.egannouncerbot.bot.Bot;
//import java.util.Timer;
//import java.util.TimerTask;
//
//@Getter
//@Setter
//@Component
//@RequiredArgsConstructor
//public class Scheduler extends Bot {
//
//    private String taskId;
//    private String[] userIdList;
//    private TimerTask timerTask;
//
////    public Scheduler(String[] userIdList, Long delay, Long period) {
////        this.userIdList = userIdList;
////        this.timerTask = createTask(userIdList);
////        Timer timer = new Timer();
////        timer.schedule(timerTask, delay, period);
////    }
////
////    public Scheduler(String[] userIdList, Long delay) {
////        this.userIdList = userIdList;
////        this.timerTask = createTask(userIdList);
////        Timer timer = new Timer();
////        timer.schedule(timerTask, delay);
////    }
//
//    public TimerTask createTask(String[] userIdList) {
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                //делаем POST запрос а АПИ
//                String reminderText = "тут будет текст";
//                //формируем сообщение
//                SendMessage sendMessage = new SendMessage();
//                sendMessage.setText(reminderText);
//                //отправляем сообщение во все чаты пользователей
//                for (String userId: userIdList) {
//                    sendMessage.setChatId(userId);
//                    try {
//                        execute(sendMessage);
//                    } catch (TelegramApiException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//        return timerTask;
//    }
//}

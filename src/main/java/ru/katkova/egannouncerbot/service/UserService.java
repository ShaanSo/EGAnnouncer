package ru.katkova.egannouncerbot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.katkova.egannouncerbot.data.User;
import ru.katkova.egannouncerbot.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

        public void createNewUser(User user) {
            log.info("User with chat id " + user.getChatId() + " was created in DB");
            userRepository.save(user);
    }
        public List<User> restoreUsersFromDB() {
            return userRepository.findAll();
    }
        public boolean existsInDB(Long chatId) {
        return !(userRepository.findFirstByChatId(chatId) == null);
        }
        @Transactional
        public void deleteUser(Long chatId) {
            log.info("User with chat id " + chatId + " was deleted from DB");
            userRepository.deleteAllByChatId(chatId);
        }
}

package ru.katkova.egannouncerbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.katkova.egannouncerbot.data.User;
import ru.katkova.egannouncerbot.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

        public void createNewUser(User user) {
            userRepository.save(user);
    }
        public List<User> restoreUsersFromDB() {
            return userRepository.findAll();
    }
        public boolean existsInDB(Long chatId) {
        return !(userRepository.findFirstByChatId(chatId) == null);
        }
}

package ru.katkova.egannouncerbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.katkova.egannouncerbot.data.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findFirstByChatId(Long chatId);

    void deleteAllByChatId(Long chatId);

}
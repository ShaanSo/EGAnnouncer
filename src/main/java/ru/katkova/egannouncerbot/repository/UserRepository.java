package ru.katkova.egannouncerbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.katkova.egannouncerbot.data.User;

/**
 * Класс взаимодействия сущности User с БД
 * createUsersTable() - создание таблицы пользователей USERS (если еще не существует, при первом старте приложения);
 * createNewUser() - создание в таблице USERS нового пользователя (при первом взаимодействии с ботом);
 * updateUser() - обновление данных по пользователю в таблице USERS (после прохождения пользователем регистрации);
 * restoreUsersFromDB() - получение объектов пользователей из базы (при старте приложения);
 * deleteUser() - удаление пользователя из таблицы USERS.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
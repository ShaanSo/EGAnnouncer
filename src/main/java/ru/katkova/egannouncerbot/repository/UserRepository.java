package ru.katkova.egannouncerbot.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.katkova.egannouncerbot.data.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс взаимодействия сущности User с БД
 * createUsersTable() - создание таблицы пользователей USERS (если еще не существует, при первом старте приложения);
 * createNewUser() - создание в таблице USERS нового пользователя (при первом взаимодействии с ботом);
 * updateUser() - обновление данных по пользователю в таблице USERS (после прохождения пользователем регистрации);
 * restoreUsersFromDB() - получение объектов пользователей из базы (при старте приложения);
 * deleteUser() - удаление пользователя из таблицы USERS.
 */

public class UserRepository {
    private static final String URL_FILE = "jdbc:h2:C:\\Data\\JavaProjects\\EpicGamesAnnouncerBot\\db/home";
    private static final String USER = "sa";
    private static final String PASSWD = "";
    private static Connection connection;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);
    static Connection getConnection() throws SQLException {
        connection = connection == null ? DriverManager.getConnection(URL_FILE, USER, PASSWD) : connection;
        return connection;
    }

    public void createUsersTable() {
        try {
            Statement st = getConnection().createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS TELEGRAM_USERS (chatId INT8 PRIMARY KEY)";
            st.execute(sql);
        } catch (SQLException e) {
            LOGGER.error("Не удалось создать таблицу TELEGRAM_USERS", e);
        }
        finally {
        }
    }

    public void createNewUser(long chatId) {
        try {
            PreparedStatement ps = getConnection().prepareStatement("INSERT INTO TELEGRAM_USERS (chatId) VALUES (?)");
            ps.setLong(1, chatId);
            ps.executeUpdate();
            LOGGER.info("Создан новый пользователь в таблице TELEGRAM_USERS");
        } catch (SQLException e) {
            LOGGER.error("Не удалось создать нового пользователя в таблице TELEGRAM_USERS", e);
        }
    }


    public List<User> restoreUsersFromDB() {
        List<User> userList = new ArrayList<>();
        try {
            Statement st = getConnection().createStatement();
            String sql = "SELECT chatId FROM TELEGRAM_USERS";
            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()) {
                userList.add(new User(resultSet.getString("chatId")));
            }
            LOGGER.info("Данные пользователей восстановлены из базы");
            return userList;
        } catch (SQLException e) {
            LOGGER.error("Не удалось восстановить данные пользователей из базы");
            throw new RuntimeException(e);
        }
    }
}
package ru.katkova.egannouncerbot.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class PromotionRepository {

    private static final String URL_FILE = "jdbc:h2:C:\\Data\\JavaProjects\\EpicGamesAnnouncerBot\\db/home";
    private static final String USER = "sa";
    private static final String PASSWD = "";
    private static Connection connection;
    private static final Logger LOGGER = LoggerFactory.getLogger(PromotionRepository.class);
    static Connection getConnection() throws SQLException {
        connection = connection == null ? DriverManager.getConnection(URL_FILE, USER, PASSWD) : connection;
        return connection;
    }

    public void createPromotionsTable() {
        try {
            Statement st = getConnection().createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS PROMOTIONS (id VARCHAR(255) PRIMARY KEY, title VARCHAR(255), description VARCHAR(255), startDate TIMESTAMP, endDate TIMESTAMP)";
            st.execute(sql);
            LOGGER.info("Создана таблица PROMOTIONS");
        } catch (SQLException e) {
            LOGGER.error("Не удалось создать таблицу PROMOTIONS", e);
        }
    }

    public void createNewPromotion(String id, String title, String description, Date startDate, Date endDate) {
        try {
            PreparedStatement ps = getConnection().prepareStatement("INSERT INTO PROMOTIONS (id, title, description, startDate, endDate) VALUES (?,?,?,?,?)");
            ps.setString(1, id);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setDate(4, startDate);
            ps.setDate(5, endDate);
            ps.executeUpdate();
            LOGGER.info("Создано новое предложение в таблице PROMOTIONS");
        } catch (SQLException e) {
            LOGGER.error("Не удалось создать новое предложение в таблице PROMOTIONS", e);
        }
    }

    public boolean existsInDB(String title, Date startDate, Date endDate) {
        try {
            PreparedStatement ps = getConnection().prepareStatement("SELECT id, title, description, startDate, endDate FROM PROMOTIONS WHERE title = ? AND startDate = ? AND endDate = ?");
            ps.setString(1, title);
            ps.setDate(2, startDate);
            ps.setDate(3, endDate);
            ps.executeQuery();
            int count = ps.getMetaData().getColumnCount();
            LOGGER.info("Создано новое предложение в таблице PROMOTIONS");
            return count > 0;
        } catch (SQLException e) {
            LOGGER.error("Не удалось провести поиск предложения по таблице PROMOTIONS", e);
            return false;
        }
    }
}

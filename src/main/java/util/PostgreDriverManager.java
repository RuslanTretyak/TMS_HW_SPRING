package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreDriverManager {
    private static PostgreDriverManager instance;
    private static String URL = "jdbc:postgresql://localhost:5432/HW32";
    private static String USERNAME = "postgres";
    private static String PASSWORD = "opelkadette";

    private PostgreDriverManager() {
        init();
    }

    private void init() {
        try {
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PostgreDriverManager getInstance() {
        if (instance == null) {
            instance = new PostgreDriverManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return connection;
    }

}
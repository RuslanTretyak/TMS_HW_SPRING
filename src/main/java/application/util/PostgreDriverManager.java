package application.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class PostgreDriverManager {
    @Value("${postgreDriverManager.url}")
    private String url;
    @Value("${postgreDriverManager.userName}")
    private String username;
    @Value("${postgreDriverManager.password}")
    private String password;

    public PostgreDriverManager() {
        init();
    }

    private void init() {
        try {
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

}
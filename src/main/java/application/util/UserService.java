package application.util;

import application.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class UserService {
    @Autowired
    private PostgreDriverManager postgreDriverManager;

    public User getUserInfo(int id) throws SQLException {
        Connection connection = this.postgreDriverManager.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users where id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        User user = new User();
        if (resultSet.next()) {
            user.setLogin(resultSet.getString(2));
            user.setName(resultSet.getString(3));
            user.setSurname(resultSet.getString(4));
            user.setAge(resultSet.getInt(5));
            return user;
        } else {
            return null;
        }
    }

    public boolean changeUserLogin(User user) {
        Connection connection = null;
        try {
            connection = this.postgreDriverManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("update users set login=? where id=?;");
            statement.setInt(2, user.getId());
            statement.setString(1, user.getLogin());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteUser(int id) {
        Connection connection = null;
        try {
            connection = this.postgreDriverManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("delete from users where id=?;");
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean createUser(User user) {
        Connection connection = null;
        try {
            connection = this.postgreDriverManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("insert into users (login, name, surname, age) values (?, ?, ?, ?);");
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getName());
            statement.setString(3, user.getSurname());
            statement.setInt(4, user.getAge());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
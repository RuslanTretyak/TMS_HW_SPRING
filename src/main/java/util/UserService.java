package util;

import entity.User;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    public static User getUserInfo(int id) throws SQLException {
        Connection connection = PostgreDriverManager.getInstance().getConnection();
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

    public static boolean changeUserLogin(User user) throws SQLException {
        Connection connection = PostgreDriverManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement("update users set login=? where id=?;");
        statement.setInt(2, user.getId());
        statement.setString(1, user.getLogin());
        return statement.executeUpdate() > 0;
    }

    public static boolean deleteUser(int id) throws SQLException {
        Connection connection = PostgreDriverManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement("delete from users where id=?;");
        statement.setInt(1, id);
        return statement.executeUpdate() > 0;
    }

    public static boolean createUser(User user) throws SQLException {
        Connection connection = PostgreDriverManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement("insert into users (login, name, surname, age) values (?, ?, ?, ?);");
        statement.setString(1, user.getLogin());
        statement.setString(2, user.getName());
        statement.setString(3, user.getSurname());
        statement.setInt(4, user.getAge());
        return statement.executeUpdate() > 0;
    }
}
package application.dao;

import application.model.User;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public User getUserInfo(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        User user = session.find(User.class, id);
        session.getTransaction().commit();
        return user;
    }

    public void createUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
    }

    public void changeLogin(int id, String newLogin) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        User user = session.find(User.class, id);
        user.setLogin(newLogin);
        session.getTransaction().commit();
    }

    /*public boolean changeUserLogin(User user) {
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


    }*/
}
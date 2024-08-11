package application.dao;

import application.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public void deleteUser(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        User user = session.find(User.class, id);
        session.remove(user);
        session.getTransaction().commit();
    }
}
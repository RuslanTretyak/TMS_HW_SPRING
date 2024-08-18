package application.dao;

import application.model.Grooup;
import application.model.Student;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Grooup> getAllGroups() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Grooup> criteriaQuery = builder.createQuery(Grooup.class);
        Root<Grooup> root = criteriaQuery.from(Grooup.class);
        criteriaQuery.select(root);
        Query<Grooup> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public List<Student> getStudentsFromGroup(String groupTitle) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = builder.createQuery(Student.class);
        Root<Student> root = criteriaQuery.from(Student.class);
        criteriaQuery.select(root).where(builder.equal(root.get("grooup").get("title"), groupTitle));
        Query<Student> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public List<Student> getStudentsOrderByRating(int page) {
        int pageSize = 10;
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = builder.createQuery(Student.class);
        Root<Student> root = criteriaQuery.from(Student.class);
        criteriaQuery.select(root).orderBy(builder.desc(root.get("recordBook").get("rating")));
        TypedQuery<Student> typedQuery = session.createQuery(criteriaQuery);
        typedQuery.setFirstResult((page - 1) * 10);
        typedQuery.setMaxResults(pageSize);
        return typedQuery.getResultList();
    }
    public List<Integer> getNumberOfPages() {
        List<Integer> result = new ArrayList<>();
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = builder.createQuery(Student.class);
        Root<Student> root = criteriaQuery.from(Student.class);
        criteriaQuery.select(root);
        if (session.createQuery(criteriaQuery).getResultList().size()%10 == 0) {
            for (int i = 1; i <= session.createQuery(criteriaQuery).getResultList().size()/10; i++) {
                result.add(i);
            }
        } else {
            for (int i = 1; i <= session.createQuery(criteriaQuery).getResultList().size()/10 + 1; i++) {
                result.add(i);
            }
        }
        return result;
    }


    public List<Student> getThreeBestRatingStudentsEveryGroup(List<Grooup> groups) {
        List<Student> resultList = new ArrayList<>();
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = builder.createQuery(Student.class);
        Root<Student> root = criteriaQuery.from(Student.class);
        for (Grooup group : groups) {
            criteriaQuery.select(root).where(builder.equal(root.get("grooup").get("title"), group.getTitle()));
            criteriaQuery.orderBy(builder.desc(root.get("recordBook").get("rating")));
            Query<Student> query = session.createQuery(criteriaQuery);
            resultList.addAll(query.getResultList().stream().limit(3).toList());
        }
        return resultList;
    }

    public List<Student> getLowRatingStudentsEveryGroup(List<Grooup> groups) {
        List<Student> resultList = new ArrayList<>();
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = builder.createQuery(Student.class);
        Root<Student> root = criteriaQuery.from(Student.class);
        for (Grooup group : groups) {
            Predicate groupTitle = builder.equal(root.get("grooup").get("title"), group.getTitle());
            Predicate rating = builder.lt(root.get("recordBook").get("rating"), getAverageRatingOfGroup(group));
            criteriaQuery.select(root).where(builder.and(groupTitle, rating));
            Query<Student> query = session.createQuery(criteriaQuery);
            resultList.addAll(query.getResultList());
        }
        return resultList;
    }


    private int getAverageRatingOfGroup(Grooup grooup) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = builder.createQuery(Student.class);
        Root<Student> root = criteriaQuery.from(Student.class);
        criteriaQuery.select(root).where(builder.equal(root.get("grooup").get("title"), grooup.getTitle()));
        Query<Student> query = session.createQuery(criteriaQuery);
        List<Student> students = query.getResultList();
        int sum = 0;
        for (Student student : students) {
            sum += student.getRecordBook().getRating();
        }
        return sum / students.size();
    }
}
package application.dao;

import application.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class StudentDAO {
    @Autowired
    private PostgresDriverManager driverManager;

    public List<Student> getAllStudents() {
        try {
            Connection connection = driverManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM students");
            ResultSet resultSet = statement.executeQuery();
            List<Student> studentList = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString(2);
                String surname = resultSet.getString(3);
                String group = resultSet.getString(4);
                int age = resultSet.getInt(5);
                int id = resultSet.getInt(1);
                studentList.add(new Student(name, surname, group, age, id));
            }
            return studentList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Student getStudent(int id) {
        try {
            Connection connection = driverManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM students where id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString(2);
                String surname = resultSet.getString(3);
                String group = resultSet.getString(4);
                int age = resultSet.getInt(5);
                return new Student(name, surname, group, age, id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void addStudent(Student student) {
        try {
            Connection connection = driverManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("insert into students (name, surname, group1, age) values (?, ?, ?, ?);");
            statement.setString(1, student.getName());
            statement.setString(2, student.getSurname());
            statement.setString(3, student.getGroup());
            statement.setInt(4, student.getAge());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteStudent(int id) {
        try {
            Connection connection = driverManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("delete from students where id=?;");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

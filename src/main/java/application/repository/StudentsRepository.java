package application.repository;

import application.model.Student;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StudentsRepository {
    private Map<Integer, Student> repository;
    private int COUNTER = 0;

    private StudentsRepository() {
        this.repository = new HashMap<>();
        this.repository.put(++COUNTER, new Student("name1", "surname1", "group1", 20, COUNTER));
        this.repository.put(++COUNTER, new Student("name2", "surname2", "group1", 21, COUNTER));
        this.repository.put(++COUNTER, new Student("name3", "surname3", "group2", 22, COUNTER));
    }

    public Map<Integer, Student> getRepository() {
        return repository;
    }

    public void addStudent(Student student) {
        student.setId(++COUNTER);
        this.repository.put(COUNTER, student);
    }
}

package application.DAO;

import application.model.Student;
import application.repository.StudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class StudentDAO {
    @Autowired
    private StudentsRepository repository;

    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        for (Map.Entry<Integer, Student> entry : repository.getRepository().entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

    public Student getStudent(int id) {
        return repository.getRepository().get(id);
    }

    public void addStudent(Student student) {
        this.repository.addStudent(student);
    }

    public void deleteStudent(int id) {
        this.repository.getRepository().remove(id);
    }

}

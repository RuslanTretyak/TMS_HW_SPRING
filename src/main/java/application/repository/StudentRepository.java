package application.repository;

import application.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findByIsPaid(boolean isPaid);

    Student findByName(String name);

    Student findBySurname(String surname);

    List<Student> findByGrooupTitleIgnoreCase(String title);
}

package application.service;

import application.entity.Grooup;
import application.entity.Student;
import application.repository.GroupRepository;
import application.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Transactional
    public List<Grooup> getAllGroups() {
        return groupRepository.findAll();
    }

    @Transactional
    public List<Student> getAllStudentFromGroup(int groupId) {
        Grooup group = groupRepository.findById(groupId).get();
        return group.getStudents();
    }

    @Transactional
    public Grooup getGroupById(int id) {
        return groupRepository.findById(Integer.valueOf(id)).get();
    }

    @Transactional
    public List<Student> getStudentsByPaid(boolean isPaid) {
        return studentRepository.findByIsPaid(isPaid);
    }

    @Transactional
    public Student getStudentById(int id) {
        return studentRepository.findById(Integer.valueOf(id)).get();
    }

    @Transactional
    public Student getStudentByName(String name) {
        return studentRepository.findByName(name);
    }

    @Transactional
    public Student getStudentBySurname(String surname) {
        return studentRepository.findBySurname(surname);
    }

    @Transactional
    public List<Student> getStudentsByGroupTitle(String title) {
        return studentRepository.findByGrooupTitleIgnoreCase(title);
    }

    @Transactional
    public void deleteStudent(int id) {
        studentRepository.deleteById(Integer.valueOf(id));
    }

    @Transactional
    public void createStudent(Student student) {
        studentRepository.save(student);
    }

    @Transactional
    public void changeStudent(int id, String newName, String newSurname, int newAge) {
        Student student = studentRepository.findById(id).get();
        student.setName(newName);
        student.setSurname(newSurname);
        student.setAge(newAge);
    }

    @Transactional
    public void moveStudentToGroup(int studentId, int groupId) {
        Student student = studentRepository.findById(studentId).get();
        student.setGrooup(groupRepository.findById(groupId).get());
    }

}

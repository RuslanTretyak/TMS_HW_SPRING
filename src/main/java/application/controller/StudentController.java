package application.controller;

import application.DAO.StudentDAO;
import application.model.Student;
import application.validator.Validator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private Validator validator;

    @GetMapping()
    public ModelAndView showStudents() {
        ModelAndView modelAndView = new ModelAndView("show_all_students");
        modelAndView.addObject("students", this.studentDAO.getAllStudents());
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView getStudentInfo(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView("student_info");
        modelAndView.addObject("student", this.studentDAO.getStudent(id));
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateStudentPage(@ModelAttribute Student student) {
        return new ModelAndView("create_student");
    }

    @PostMapping
    public ModelAndView createStudent(@Valid @ModelAttribute Student student, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("create_student", "student", student);
            return modelAndView;
        } else {
            studentDAO.addStudent(student);
            return new ModelAndView("created_successful", "student", student);
        }
    }

    @GetMapping("/delete")
    public ModelAndView showDeleteStudentPage() {
        return new ModelAndView("delete_student");
    }

    @PostMapping("/delete")
    public ModelAndView deleteStudent(@RequestParam int id) {
        if (this.validator.isIdValid(id)) {
            this.studentDAO.deleteStudent(id);
            return new ModelAndView("deleted_successful").addObject("id", id);
        } else {
            return new ModelAndView("delete_student").addObject("message", "id " + id + " is not found");
        }
    }

}

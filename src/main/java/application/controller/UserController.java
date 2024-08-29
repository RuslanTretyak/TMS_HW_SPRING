package application.controller;

import application.entity.Student;
import application.service.DBService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/group")
public class UserController {
    @Autowired
    private DBService dbService;


    @GetMapping()
    public ModelAndView showGroups() {
        ModelAndView modelAndView = new ModelAndView("groups");
        modelAndView.addObject("groups", dbService.getAllGroups());
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView showStudentsFromGroup(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView("students");
        modelAndView.addObject("students", dbService.getAllStudentFromGroup(id));
        return modelAndView;
    }

    @GetMapping("/students/notpaid")
    public ModelAndView showStudentsNotPaid() {
        ModelAndView modelAndView = new ModelAndView("students");
        modelAndView.addObject("students", dbService.getStudentsByPaid(false));
        return modelAndView;
    }

    @GetMapping("/student/name/{name}")
    public ModelAndView showStudentByName(@PathVariable String name) {
        ModelAndView modelAndView = new ModelAndView("student");
        modelAndView.addObject("student", dbService.getStudentByName(name));
        return modelAndView;
    }

    @GetMapping("/student/surname/{surname}")
    public ModelAndView showStudentBySurname(@PathVariable String surname) {
        ModelAndView modelAndView = new ModelAndView("student");
        modelAndView.addObject("student", dbService.getStudentBySurname(surname));
        return modelAndView;
    }

    @GetMapping("/student/create")
    public ModelAndView showCreatePage(@ModelAttribute Student student) {
        return new ModelAndView("create_student");
    }

    @PostMapping("/student/create")
    public ModelAndView createStudent(@RequestParam String isPaid, @RequestParam int groupId, @Valid @ModelAttribute Student student, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("create_student", "student", student);
            return modelAndView;
        } else {
            student.setGrooup(dbService.getGroupById(groupId));
            student.setPaid(Boolean.valueOf(isPaid));
            dbService.createStudent(student);
            return new ModelAndView("redirect:/group");
        }
    }

    @GetMapping("/title/{title}")
    public ModelAndView showStudentsFromGroup(@PathVariable String title) {
        ModelAndView modelAndView = new ModelAndView("students");
        modelAndView.addObject("students", dbService.getStudentsByGroupTitle(title));
        return modelAndView;
    }

    @PostMapping("/student/delete/{id}")
    public String deleteStudent(@PathVariable int id) {
        dbService.deleteStudent(id);
        return "redirect:/group";
    }

    @GetMapping("/student/change/{id}")
    public ModelAndView showChangePage(@PathVariable int id, @ModelAttribute Student student) {
        System.out.println(dbService.getStudentById(id));
        return new ModelAndView("change_student", "student", dbService.getStudentById(id));
    }

    @PostMapping("/student/change/{id}")
    public ModelAndView createStudent(@PathVariable int id, @Valid @ModelAttribute Student student, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("change_student", "student", student);
        } else {
            dbService.changeStudent(id, student.getName(), student.getSurname(), student.getAge());
            return new ModelAndView("redirect:/group");
        }
    }

    @GetMapping("/student/change_group/{student_id}")
    public ModelAndView showChangeGroupPage(@PathVariable(name = "student_id") int studentId) {
        ModelAndView modelAndView = new ModelAndView("choose_group", "groups", dbService.getAllGroups());
        modelAndView.addObject("student_id", studentId);
        return modelAndView;
    }

    @PostMapping("/student/change_group/{id}")
    public ModelAndView ChangeGroup(@PathVariable int id, @RequestParam(name = "student_id") int studentId) {
        dbService.moveStudentToGroup(studentId, id);
        return new ModelAndView("redirect:/group");
    }
}

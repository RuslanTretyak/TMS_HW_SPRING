package application.controller;

import application.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/group")
public class UserController {
    @Autowired
    private UserDAO userDAO;

    @GetMapping()
    public ModelAndView showGroups() {
        ModelAndView modelAndView = new ModelAndView("groups");
        modelAndView.addObject("groups", userDAO.getAllGroups());
        return modelAndView;
    }
    @GetMapping("/{groupTitle}")
    public ModelAndView showStudentsFromGroup(@PathVariable String groupTitle) {
        ModelAndView modelAndView = new ModelAndView("students");
        modelAndView.addObject("students", userDAO.getStudentsFromGroup(groupTitle));
        return modelAndView;
    }
    @GetMapping("/students_sorted/{page}")
    public ModelAndView showStudentsSortedByRating(@PathVariable(name = "page") int page) {
        ModelAndView modelAndView = new ModelAndView("students_sorted");
        modelAndView.addObject("students", userDAO.getStudentsOrderByRating(page));
        modelAndView.addObject("page", page);
        modelAndView.addObject("numberOfPages", userDAO.getNumberOfPages());
        return modelAndView;
    }
    @GetMapping("/top_3_students_from_each_group")
    public ModelAndView showThreeBestRatingStudentsEveryGroup() {
        ModelAndView modelAndView = new ModelAndView("students_top_3");
        modelAndView.addObject("students", userDAO.getThreeBestRatingStudentsEveryGroup(userDAO.getAllGroups()));
        return modelAndView;
    }
    @GetMapping("/students_performance_lower_average")
    public ModelAndView showLowRatingStudentsEveryGroup() {
        ModelAndView modelAndView = new ModelAndView("students_top_3");
        modelAndView.addObject("students", userDAO.getLowRatingStudentsEveryGroup(userDAO.getAllGroups()));
        return modelAndView;
    }
}

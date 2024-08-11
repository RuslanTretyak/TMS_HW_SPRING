package application.controller;

import application.model.User;
import application.dao.UserDAO;
import application.util.Validator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private Validator validator;

    @GetMapping("/get/{id}")
    public ModelAndView getUserInfo(@PathVariable int id, Model model) {
        ModelAndView modelAndView = new ModelAndView("user_info");
        modelAndView.addObject("user", userDAO.getUserInfo(id));
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateUserPage(@ModelAttribute User user) {
        return new ModelAndView("create_user");
    }

    @PostMapping
    public ModelAndView createUser(@Valid @ModelAttribute User user, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("create_user", "user", user);
        } else {
            userDAO.createUser(user);
            return new ModelAndView("created_successful", "user", user);
        }
    }

    @GetMapping("/change")
    public ModelAndView showChangeLoginPage() {
        return new ModelAndView("change_login");
    }

    @PostMapping("/change")
    public ModelAndView changeStudent(@RequestParam int id, @RequestParam String newLogin) {
        if (validator.isIdValid(id)) {
            if (!newLogin.isEmpty()) {
                userDAO.changeLogin(id, newLogin);
                return new ModelAndView("change_successful");
            } else {
                return new ModelAndView("change_login").addObject("message", "new login not entered");
            }
        } else {
            return new ModelAndView("change_login").addObject("message", "id " + id + " is not found");
        }
    }

    @GetMapping("/delete")
    public ModelAndView showDeleteUserPage() {
        return new ModelAndView("delete_user");
    }

    @PostMapping("/delete")
    public ModelAndView deleteUser(@RequestParam int id) {
        if (validator.isIdValid(id)) {
            userDAO.deleteUser(id);
            return new ModelAndView("delete_successful");
        } else {
            return new ModelAndView("delete_user").addObject("message", "id " + id + " is not found");
        }
    }
}

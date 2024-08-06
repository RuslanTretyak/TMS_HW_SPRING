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

import java.sql.SQLException;

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
        if (validator.isIdValid(id) && !newLogin.isEmpty()) {
            userDAO.changeLogin(id, newLogin);
            return new ModelAndView("deleted_successful").addObject("id", id);
        } else {
            return new ModelAndView("delete_student").addObject("message", "id " + id + " is not found");
        }
    }


    /*@RequestMapping(value = "/create")
    public ModelAndView showCreateUserForm() {
        return new ModelAndView("create_user", "user", new User());
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createUser(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            this.userService.createUser(user);
            model.addAttribute("message", "user " + user.getLogin() + " created successful");
            return new ModelAndView("create_user_result", "model", model);
        }
        model.addAttribute("message", "not all data was entered!");
        return new ModelAndView("create_user", "user", new User()).addObject(model);
    }

    @RequestMapping(value = "/delete")
    public String showDeleteUserForm() {
        return "delete_user";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView deleteUser(@RequestParam int id, Model model) {
        if (this.userService.deleteUser(id)) {
            model.addAttribute("message", "user with ID " + id + " was deleted");
        } else {
            model.addAttribute("message", "user with ID " + id + " does not exist");
        }
        return new ModelAndView("delete_user_result", "model", model);
    }

    @RequestMapping(value = "/change")
    public ModelAndView showChangeLoginForm() {
        return new ModelAndView("change_login", "user", new User());
    }

    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public ModelAndView changeLogin(@ModelAttribute User user, Model model) {
        if (!user.getLogin().isEmpty()) {
            if (this.userService.changeUserLogin(user)) {
                model.addAttribute("message", "new login applied");
            } else {
                model.addAttribute("message", "user with ID " + user.getId() + " does not exist");
            }
            return new ModelAndView("change_login_result", "model", model);
        } else {
            model.addAttribute("message", "not all data was entered!");
            return new ModelAndView("change_login", "user", new User()).addObject(model);
        }
    }*/

}

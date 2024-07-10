package application.controller;

import application.entity.User;
import application.util.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/get-user")
    public String getUserInfo(@RequestParam int id, Model model) {
        User user = null;
        try {
            user = this.userService.getUserInfo(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (user != null) {
            model.addAttribute("isReqSuccessful", true);
            model.addAttribute("name", user.getName());
            model.addAttribute("surname", user.getSurname());
            model.addAttribute("login", user.getLogin());
            model.addAttribute("age", user.getAge());
        } else {
            model.addAttribute("isReqSuccessful", false);
            model.addAttribute("message", "id " + id + " not found");
        }
        return "userinfo";
    }


    @RequestMapping(value = "/create")
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
    }

}

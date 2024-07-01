package controller;

import entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import util.UserService;

import java.sql.SQLException;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/get")
    public String getUserInfo(@RequestParam int id, Model model) {
        User user = null;
        try {
            user = UserService.getUserInfo(id);
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
    public ModelAndView createUser(@ModelAttribute User user, Model model) {
        if (!user.getLogin().isEmpty() && !user.getName().isEmpty() && !user.getSurname().isEmpty()) {
            try {
                UserService.createUser(user);
                model.addAttribute("message", "user " + user.getLogin() + " created successful");
                return new ModelAndView("create_user_result", "model", model);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        model.addAttribute("message", "not all data was entered!");
        return new ModelAndView("create_user", "user", new User()).addObject(model);
    }

    @RequestMapping(value = "/delete")
    public ModelAndView showDeleteUserForm() {
        return new ModelAndView("delete_user", "user", new User());
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView deleteUser(@ModelAttribute User user, Model model) {
        try {
            if (UserService.deleteUser(user.getId())) {
                model.addAttribute("message", "user with ID " + user.getId() + " was deleted");
            } else {
                model.addAttribute("message", "user with ID " + user.getId() + " does not exist");
            }
            return new ModelAndView("delete_user_result", "model", model);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @RequestMapping(value = "/change")
    public ModelAndView showChangeLoginForm() {
        return new ModelAndView("change_login", "user", new User());
    }

    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public ModelAndView changeLogin(@ModelAttribute User user, Model model) {
        if (!user.getLogin().isEmpty()) {
            try {
                if (UserService.changeUserLogin(user)) {
                    model.addAttribute("message", "new login applied");
                } else {
                    model.addAttribute("message", "user with ID " + user.getId() + " does not exist");
                }
                return new ModelAndView("change_login_result", "model", model);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            model.addAttribute("message", "not all data was entered!");
            return new ModelAndView("change_login", "user", new User()).addObject(model);
        }
    }

}

package application.util;

import application.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Validator {
    @Autowired
    private UserDAO userDAO;

    public boolean isIdValid(int id) {
        if (userDAO.getUserInfo(id) != null) {
            return true;
        }
        return false;
    }
}

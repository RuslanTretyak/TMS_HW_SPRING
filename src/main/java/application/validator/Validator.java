package application.validator;

import application.dao.StudentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Validator {
    @Autowired
    private StudentDAO studentDAO;

    public boolean isIdValid(int id) {
        return studentDAO.getStudent(id) != null;
    }
}

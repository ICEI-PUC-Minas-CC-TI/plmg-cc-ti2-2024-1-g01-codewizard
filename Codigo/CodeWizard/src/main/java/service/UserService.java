package service;
import com.google.gson.Gson;
import dao.USERDAO;
import model.USER;
import java.util.List;

public class UserService {

    public String getAllProfessors() {
        USERDAO userDao = new USERDAO();
        List<USER> professors = userDao.getAllProfessors();
        return new Gson().toJson(professors);
    }
    public String login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return null; // Retorna nulo se os campos estiverem vazios
        }

        USERDAO userDao = new USERDAO();
        USER user = userDao.verifyCredentials(username, password);

        if (user != null) {
            return "Login Successful";
        } else {
            return "Invalid Credentials";
        }
    }
}
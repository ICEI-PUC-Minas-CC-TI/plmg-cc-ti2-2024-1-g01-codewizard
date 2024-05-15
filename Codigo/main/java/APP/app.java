package APP;

import static spark.Spark.*;

import java.util.Arrays;
import java.util.List;

import dao.USERDAO;
import model.USER;
import service.UserService;
import service.VideoService;
import service.questionservice;


public class app {
    public static void main(String[] args) {
        port(4567);
        staticFiles.location("/public");
        
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET, POST");
            response.header("Access-Control-Allow-Headers", "Content-Type");
         // Rotas que não requerem autenticação
            List<String> publicRoutes = Arrays.asList("/login", "/login.html", "/register-user");

            if (!publicRoutes.contains(request.pathInfo())) {
                if (request.session().attribute("user") == null) {
                	response.redirect("/login.html");
                    halt();
                }
            }
        });

        questionservice questionService = new questionservice();

        get("/questions", (req, res) -> questionService.getAllQuestions(req, res));
        post("/submit-answers", (req, res) -> questionService.submitAnswers(req, res));
        post("/submit-question", (req, res) -> questionService.createQuestion(req, res));
        get("/get-questions", (req, res) -> questionService.getQuestions(req, res));
        get("/logout", (req, res) -> {
            req.session().removeAttribute("user");
            req.session().invalidate();
            res.redirect("/login.html");
            return null;
        });
        
       

        UserService userService = new UserService();

        get("/professors", (req, res) -> {res.type("application/json");
            return userService.getAllProfessors();
        });
        
        VideoService videoService = new VideoService();
        
        get("/videos", videoService::listVideos);
        post("/upload", videoService::uploadVideo);
        
        post("/register-user", (req, res) -> {
            // Obter os parâmetros do formulário
            String username = req.queryParams("username");
            String email = req.queryParams("email");
            String password = req.queryParams("password");
            String role = req.queryParams("role"); // 'aluno' ou 'professor'

            // Criar um novo objeto USER com os dados do formulário
            USER newUser = new USER(-1, username, email, role, password);

            // Inserir o novo usuário no banco de dados
            USERDAO userDao = new USERDAO();
            boolean success = userDao.insert(newUser);

            // Responder com uma mensagem de sucesso ou erro
            res.type("text/html");
            if (success) {
                return "<script>alert('Usuario cadastrado com sucesso!'); window.location.href = '/home.html';</script>";
            } else {
                return "<script>alert('Erro ao cadastrar usuario.'); window.history.back();</script>";
            }
        });
        post("/login", (req, res) -> {
            String username = req.queryParams("username");
            String password = req.queryParams("password");

            if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
                res.status(400);  // Bad Request
                return "Username and password cannot be empty";
            }

            USERDAO userDao = new USERDAO();
            USER user = userDao.verifyCredentials(username, password);

            if (user != null) {
                req.session(true).attribute("user", user.getUserID());  // Inicia a sessão e armazena o userID
                res.status(200);  // OK
                return "Login Successful";
            } else {
                res.status(401);  // Unauthorized
                return "Invalid Credentials";
            }
        });
        get("/user-info", (req, res) -> {
            res.type("application/json");
            Integer userId = req.session().attribute("user");
            if (userId == null) {
                res.status(401);
                return "{}";
            } else {
                USERDAO userDao = new USERDAO();
                USER user = userDao.get(userId);
                if (user != null) {
                    return String.format("{\"name\": \"%s\", \"role\": \"%s\"}", user.getUsername(), user.getRole());
                } else {
                    res.status(404);
                    return "{}";
                }
            }
        });
   
    }
}

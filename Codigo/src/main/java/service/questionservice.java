package service;

import com.google.gson.Gson;
import dao.QUESTIONDAO;
import model.QUESTION;
import spark.Request;
import spark.Response;

import java.util.List;

public class questionservice {

    private QUESTIONDAO questionDao;

    public questionservice() {
        this.questionDao = new QUESTIONDAO(); // Inicializa o DAO dentro do serviço
    }

    public String getAllQuestions(Request req, Response res) {
        List<QUESTION> questions = questionDao.getAll();
        res.type("application/json");
        return new Gson().toJson(questions);
    }

    public String submitAnswers(Request req, Response res) {
        // Aqui você pode adicionar lógica para processar as respostas se necessário
        return "Answers processed";
    }

    public String createQuestion(Request req, Response res) {
        String content = req.queryParams("content");
        int videoID = Integer.parseInt(req.queryParams("videoID"));
        String level = req.queryParams("level");

        QUESTION newQuestion = new QUESTION(-1, content, videoID, level);
        questionDao.insert(newQuestion);

        res.type("text/html");
        return "<script>alert('Pergunta cadastrada com sucesso!'); window.location.href = '/home.html';</script>";
    }

    public String getQuestions(Request req, Response res) {
        List<QUESTION> allQuestions = questionDao.getAll();
        res.type("application/json");
        return new Gson().toJson(allQuestions);
    }
}

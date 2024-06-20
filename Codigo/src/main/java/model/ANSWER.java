package model;

import java.sql.Timestamp;

import java.util.List;
import java.util.stream.Collectors;

public class ANSWER {
    private int answerID;
    private int questionID;
    private int userID;
    private boolean answer;
    private Timestamp timestamp;

    // Construtor padrão
    public ANSWER() {
        this.answerID = -1;
        this.questionID = -1;
        this.userID = -1;
        this.answer = false;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    // Construtor com todos os parâmetros
    public ANSWER(int answerID, int questionID, int userID, boolean answer, Timestamp timestamp) {
        this.answerID = answerID;
        this.questionID = questionID;
        this.userID = userID;
        this.answer = answer;
        this.timestamp = timestamp;
    }

    // Getters e Setters
    public int getAnswerID() {
        return answerID;
    }

    public void setAnswerID(int answerID) {
        this.answerID = answerID;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    // Método toString
    @Override
    public String toString() {
        return "ANSWER [answerID=" + answerID + ", questionID=" + questionID + ", userID=" + userID +
               ", answer=" + answer + ", timestamp=" + timestamp + "]";
    }
    // Métodos estáticos para filtrar respostas na memória (útil se já tiver todas as respostas carregadas)
    public static List<ANSWER> filterByUser(List<ANSWER> answers, int userID) {
        return answers.stream()
                      .filter(a -> a.getUserID() == userID)
                      .collect(Collectors.toList());
    }

    public static List<ANSWER> filterByTruthValue(List<ANSWER> answers, boolean truthValue) {
        return answers.stream()
                      .filter(a -> a.isAnswer() == truthValue)
                      .collect(Collectors.toList());
    }
}


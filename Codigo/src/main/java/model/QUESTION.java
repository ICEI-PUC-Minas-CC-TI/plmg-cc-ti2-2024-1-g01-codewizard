package model;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;



public class QUESTION {
    private int questionID;
    private String content;
    private int videoID;  // Foreign key relation to VIDEO
    private String level; // New field to store the difficulty level of the question

    // Construtor padrão
    public QUESTION() {
        this.questionID = -1;
        this.content = "";
        this.videoID = -1;
        this.level = "easy"; // Default value can be set to 'easy' or any other valid level
    }

    // Construtor com todos os parâmetros
    public QUESTION(int questionID, String content, int videoID, String level) {
        this.questionID = questionID;
        this.content = content;
        this.videoID = videoID;
        this.level = level;
    }

    // Getters e Setters
    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getVideoID() {
        return videoID;
    }

    public void setVideoID(int videoID) {
        this.videoID = videoID;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    // Método toString
    @Override
    public String toString() {
        return "QUESTION [questionID=" + questionID + ", content=" + content + ", videoID=" + videoID + ", level=" + level + "]";
    }
 // Método para ordenar uma lista de perguntas pelo level
    public static List<QUESTION> sortByLevel(List<QUESTION> questions, String specificLevel) {
        return questions.stream()
                        .filter(q -> q.getLevel().equalsIgnoreCase(specificLevel))
                        .sorted(Comparator.comparing(QUESTION::getQuestionID))
                        .collect(Collectors.toList());
    }

}

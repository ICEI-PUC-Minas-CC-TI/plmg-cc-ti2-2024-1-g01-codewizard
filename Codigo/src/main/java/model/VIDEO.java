package model;



public class VIDEO {
    private int videoID;
    private String title;
    private String description;
    private String url;

    // Construtor padrão
    public VIDEO() {
        this.videoID = -1;
        this.title = "";
        this.description = "";
        this.url = "";
    }

    // Construtor com todos os parâmetros
    public VIDEO(int videoID, String title, String description, String url) {
        this.videoID = videoID;
        this.title = title;
        this.description = description;
        this.url = url;
    }

    // Getters e Setters
    public int getVideoID() {
        return videoID;
    }

    public void setVideoID(int videoID) {
        this.videoID = videoID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // Método toString
    @Override
    public String toString() {
        return "VIDEO [videoID=" + videoID + ", title=" + title + ", description=" + description + ", url=" + url + "]";
    }
}


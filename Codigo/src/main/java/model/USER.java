package model;

public class USER {
    private int userID;
    private String username;
    private String email;
    private String role;
    private String password;

    // Construtor padrão
    public USER() {
        this.userID = -1;
        this.username = "";
        this.email = "";
        this.role = "";
        this.password = "";
    }

    // Construtor com todos os parâmetros
    public USER(int userID, String username, String email, String role, String password) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    // Getters e Setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Método toString
    @Override
    public String toString() {
        return "User [userID=" + userID + ", username=" + username + ", email=" + email + ", role=" + role + ", password=" + password + "]";
    }
}

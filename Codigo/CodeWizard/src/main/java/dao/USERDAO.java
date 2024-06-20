package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.USER;

public class USERDAO extends DAO {

    public USERDAO() {
        super();
        conectar();
    }

    public void finalize() {
        close();
    }

    public boolean insert(USER user) {
        boolean status = false;
        try {
            PreparedStatement st = conexao.prepareStatement("INSERT INTO \"USER\" (username, email, role, password) VALUES (?, ?, ?::role_type, ?);");
            st.setString(1, user.getUsername());
            st.setString(2, user.getEmail());
            st.setString(3, user.getRole());
            st.setString(4, user.getPassword());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public USER get(int userID) {
        USER user = null;

        try {
            PreparedStatement st = conexao.prepareStatement("SELECT * FROM \"USER\" WHERE \"userID\" = ?;",  // Nota a mudança aqui
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.setInt(1, userID);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                user = new USER(rs.getInt("userID"), rs.getString("username"), rs.getString("email"), rs.getString("role"), rs.getString("password"));
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return user;
    }
    

    public List<USER> getAll() {
        List<USER> users = new ArrayList<USER>();

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM \"USER\";");
            while (rs.next()) {
                USER user = new USER(rs.getInt("userID"), rs.getString("username"), rs.getString("email"), rs.getString("role"), rs.getString("password"));
                users.add(user);
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return users;
    }
    public List<USER> getAllProfessors() {
        List<USER> professors = new ArrayList<USER>();

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            // Adiciona a condição WHERE para filtrar apenas os professores
            ResultSet rs = st.executeQuery("SELECT * FROM \"USER\" WHERE role = 'professor';");
            while (rs.next()) {
                USER user = new USER(
                    rs.getInt("userID"), 
                    rs.getString("username"), 
                    rs.getString("email"), 
                    rs.getString("role"), 
                    rs.getString("password")
                );
                professors.add(user);
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return professors;
    }


    public boolean update(USER user) {
        boolean status = false;
        try {
            PreparedStatement st = conexao.prepareStatement("UPDATE \"USER\" SET \"username\" = ?, \"email\" = ?, \"role\" = ?::role_type, \"password\" = ? WHERE \"userID\" = ?;");
            st.setString(1, user.getUsername());
            st.setString(2, user.getEmail());
            st.setString(3, user.getRole().toString());
            st.setString(4, user.getPassword());
            st.setInt(5, user.getUserID());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public boolean delete(int userID) {
        boolean status = false;
        try {
            PreparedStatement st = conexao.prepareStatement("DELETE FROM \"USER\" WHERE \"userID\" = ?;");
            st.setInt(1, userID);
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }
    public USER verifyCredentials(String username, String password) {
        USER user = null;
        try {
            PreparedStatement st = conexao.prepareStatement("SELECT * FROM \"USER\" WHERE username = ? AND password = ?;");
            st.setString(1, username);
            st.setString(2, password);  // Certifique-se de que a senha está sendo criptografada antes da comparação
            ResultSet rs = st.executeQuery();
            if (rs.next()) {  // Verifica se existe algum resultado
                user = new USER(rs.getInt("userID"), rs.getString("username"), rs.getString("email"), rs.getString("role"), rs.getString("password"));
            }
            st.close();
        } catch (Exception e) {
            System.err.println("Erro ao verificar credenciais: " + e.getMessage());
        }
        return user;
    }
}


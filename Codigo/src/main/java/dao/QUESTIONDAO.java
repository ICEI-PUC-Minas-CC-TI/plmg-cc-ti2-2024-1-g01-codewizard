package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.QUESTION;

public class QUESTIONDAO extends DAO {
    
    public QUESTIONDAO() {
        super();
        conectar();
    }

    public void finalize() {
        close();
    }

    public boolean insert(QUESTION question) {
        boolean status = false;
        try {
            PreparedStatement st = conexao.prepareStatement("INSERT INTO \"QUESTION\" (content, videoID, level) VALUES (?, ?, ?::level_type);");
            st.setString(1, question.getContent());
            st.setInt(2, question.getVideoID());
            st.setString(3, question.getLevel());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public QUESTION get(int questionID) {
        QUESTION question = null;

        try {
            PreparedStatement st = conexao.prepareStatement("SELECT * FROM \"QUESTION\" WHERE \"questionID\" = ?;",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.setInt(1, questionID);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                question = new QUESTION(rs.getInt("questionID"), rs.getString("content"), rs.getInt("videoID"), rs.getString("level"));
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return question;
    }

    public List<QUESTION> getAll() {
        List<QUESTION> questions = new ArrayList<>();

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM \"QUESTION\";");
            while (rs.next()) {
                questions.add(new QUESTION(rs.getInt("questionID"), rs.getString("content"), rs.getInt("videoID"), rs.getString("level")));
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return questions;
    }

    public boolean update(QUESTION question) {
        boolean status = false;
        try {
            PreparedStatement st = conexao.prepareStatement("UPDATE \"QUESTION\" SET content = ?, videoID = ?, level = ?::level_type WHERE \"questionID\" = ?;");
            st.setString(1, question.getContent());
            st.setInt(2, question.getVideoID());
            st.setString(3, question.getLevel());
            st.setInt(4, question.getQuestionID());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public boolean delete(int questionID) {
        boolean status = false;
        try {
            PreparedStatement st = conexao.prepareStatement("DELETE FROM \"QUESTION\" WHERE \"questionID\" = ?;");
            st.setInt(1, questionID);
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }
}


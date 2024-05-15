package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.ANSWER;

public class ANSWERDAO extends DAO {
    
    public ANSWERDAO() {
        super();
        conectar();
    }

    public void finalize() {
        close();
    }

    public boolean insert(ANSWER answer) {
        boolean status = false;
        try {
            PreparedStatement st = conexao.prepareStatement("INSERT INTO \"ANSWER\" (questionID, userID, answer, timestamp) VALUES (?, ?, ?, ?);");
            st.setInt(1, answer.getQuestionID());
            st.setInt(2, answer.getUserID());
            st.setBoolean(3, answer.isAnswer());
            st.setTimestamp(4, answer.getTimestamp());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public ANSWER get(int answerID) {
        ANSWER answer = null;
        try {
            PreparedStatement st = conexao.prepareStatement("SELECT * FROM \"ANSWER\" WHERE \"answerID\" = ?;",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.setInt(1, answerID);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                answer = new ANSWER(rs.getInt("answerID"), rs.getInt("questionID"), rs.getInt("userID"),
                                    rs.getBoolean("answer"), rs.getTimestamp("timestamp"));
            }
            st.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return answer;
    }

    public List<ANSWER> getAll() {
        List<ANSWER> answers = new ArrayList<>();
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM \"ANSWER\";");
            while (rs.next()) {
                answers.add(new ANSWER(rs.getInt("answerID"), rs.getInt("questionID"), rs.getInt("userID"),
                                        rs.getBoolean("answer"), rs.getTimestamp("timestamp")));
            }
            st.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return answers;
    }

    public boolean update(ANSWER answer) {
        boolean status = false;
        try {
            PreparedStatement st = conexao.prepareStatement("UPDATE \"ANSWER\" SET questionID = ?, userID = ?, answer = ?, timestamp = ? WHERE \"answerID\" = ?;");
            st.setInt(1, answer.getQuestionID());
            st.setInt(2, answer.getUserID());
            st.setBoolean(3, answer.isAnswer());
            st.setTimestamp(4, answer.getTimestamp());
            st.setInt(5, answer.getAnswerID());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public boolean delete(int answerID) {
        boolean status = false;
        try {
            PreparedStatement st = conexao.prepareStatement("DELETE FROM \"ANSWER\" WHERE \"answerID\" = ?;");
            st.setInt(1, answerID);
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }
    
    public List<ANSWER> getAnswersByUser(int userID) {
        List<ANSWER> answers = new ArrayList<>();
        try {
            // Corrigindo a consulta SQL para usar o nome correto da coluna
            PreparedStatement st = conexao.prepareStatement("SELECT * FROM \"ANSWER\" WHERE \"userid\" = ?;");
            st.setInt(1, userID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                answers.add(new ANSWER(
                    rs.getInt("answerID"),
                    rs.getInt("questionID"),
                    rs.getInt("userID"),
                    rs.getBoolean("answer"),
                    rs.getTimestamp("timestamp")
                ));
            }
            st.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return answers;
    }


    public List<ANSWER> getAnswersByTruthValue(boolean truthValue) {
        List<ANSWER> answers = new ArrayList<>();
        try {
            PreparedStatement st = conexao.prepareStatement("SELECT * FROM \"ANSWER\" WHERE \"answer\" = ?;");
            st.setBoolean(1, truthValue);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                answers.add(new ANSWER(rs.getInt("answerID"), rs.getInt("questionID"), rs.getInt("userID"),
                                        rs.getBoolean("answer"), rs.getTimestamp("timestamp")));
            }
            st.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return answers;
    }
}


package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.VIDEO;

public class VIDEODAO extends DAO {
    
    public VIDEODAO() {
        super();
        conectar();
    }

    public void finalize() {
        close();
    }

    public boolean insert(VIDEO video) {
        boolean status = false;
        try {
            PreparedStatement st = conexao.prepareStatement("INSERT INTO \"VIDEO\" (title, description, url) VALUES (?, ?, ?);");
            st.setString(1, video.getTitle());
            st.setString(2, video.getDescription());
            st.setString(3, video.getUrl());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public VIDEO get(int videoID) {
        VIDEO video = null;

        try {
            PreparedStatement st = conexao.prepareStatement("SELECT * FROM \"VIDEO\" WHERE \"videoID\" = ?;",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.setInt(1, videoID);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                video = new VIDEO(rs.getInt("videoID"), rs.getString("title"), rs.getString("description"), rs.getString("url"));
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return video;
    }

    public List<VIDEO> getAll() {
        List<VIDEO> videos = new ArrayList<VIDEO>();

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM \"VIDEO\";");
            while (rs.next()) {
                VIDEO video = new VIDEO(rs.getInt("videoID"), rs.getString("title"), rs.getString("description"), rs.getString("url"));
                videos.add(video);
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return videos;
    }

    public boolean update(VIDEO video) {
        boolean status = false;
        try {
            PreparedStatement st = conexao.prepareStatement("UPDATE \"VIDEO\" SET title = ?, description = ?, url = ? WHERE \"videoID\" = ?;");
            st.setString(1, video.getTitle());
            st.setString(2, video.getDescription());
            st.setString(3, video.getUrl());
            st.setInt(4, video.getVideoID());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public boolean delete(int videoID) {
        boolean status = false;
        try {
            PreparedStatement st = conexao.prepareStatement("DELETE FROM \"VIDEO\" WHERE \"videoID\" = ?;");
            st.setInt(1, videoID);
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }
}


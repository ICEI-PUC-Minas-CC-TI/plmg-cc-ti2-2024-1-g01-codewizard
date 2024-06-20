package service;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;

import org.apache.commons.fileupload.FileItem;
import spark.Request;
import spark.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VideoService {

    private final String videoDirectory = "src/main/resources/public/videos";

    public String listVideos(Request req, Response res) {
        File folder = new File(videoDirectory);
        File[] listOfFiles = folder.listFiles();
        List<String> videos = new ArrayList<>();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                videos.add(file.getName());
            }
        }
        res.type("application/json");
        return new Gson().toJson(videos);
    }

    public String uploadVideo(Request req, Response res) {
        File uploadDir = new File(videoDirectory);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        try {
            List<FileItem> formItems = upload.parseRequest(req.raw());
            if (formItems != null && formItems.size() > 0) {
                for (FileItem item : formItems) {
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        String filePath = videoDirectory + File.separator + fileName;
                        File storeFile = new File(filePath);
                        item.write(storeFile);
                        return "Upload realizado com sucesso: " + fileName;
                    }
                }
            }
        } catch (Exception e) {
            res.status(500);
            return "Erro no servidor: " + e.getMessage();
        }
        return "Erro no upload";
    }
}

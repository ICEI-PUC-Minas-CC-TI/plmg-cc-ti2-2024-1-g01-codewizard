package service;
import com.microsoft.azure.cognitiveservices.vision.computervision.ComputerVisionClient;

import com.microsoft.azure.cognitiveservices.vision.computervision.ComputerVisionManager;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;


public class VisionService {
    private ComputerVisionClient compVisClient;

    public VisionService(String subscriptionKey, String endpoint) {
        this.compVisClient = ComputerVisionManager.authenticate(subscriptionKey).withEndpoint(endpoint);
    }

    public String recognizeTextFromImage(InputStream imageStream) throws Exception {
        // Converte InputStream para byte array
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];  // Buffer size
        while ((nRead = imageStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        byte[] byteArray = buffer.toByteArray();

        // Executa a operação de OCR
        OcrResult ocrResult = this.compVisClient.computerVision()
            .recognizePrintedTextInStream()
            .withDetectOrientation(true)
            .withImage(byteArray)
            .execute();

        // Processa o resultado
        StringBuilder resultText = new StringBuilder();
        for (OcrRegion region : ocrResult.regions()) {
            for (OcrLine line : region.lines()) {
                for (OcrWord word : line.words()) {
                    resultText.append(word.text()).append(" ");
                }
                resultText.append("\n");
            }
        }
        return resultText.toString();
    }
}


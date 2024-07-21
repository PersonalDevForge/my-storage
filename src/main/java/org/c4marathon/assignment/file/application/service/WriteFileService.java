package org.c4marathon.assignment.file.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class WriteFileService {

    public String writeFile(String folderPath, String uuidFileName, String type, byte[] file) {
        String uploadServerPath = folderPath;
        String uploadServerFilePath = uploadServerPath + "/" + uuidFileName + "." + type;
        File uploadServerDir = new File(uploadServerPath);
        File uploadServerFile = new File(uploadServerFilePath);

        // 파일이 존재한다면 실패한다.
        if (uploadServerFile.exists()) {
            throw new RuntimeException("File already exists");
        }
        // 폴더가 존재하지 않는다면 생성한다.
        if (!uploadServerDir.exists()) {
            if (!uploadServerDir.mkdirs()) {
                throw new RuntimeException("Failed to create directory");
            }
        }

        try {
            // 파일을 생성한다.
            if (!uploadServerFile.createNewFile()) {
                throw new RuntimeException("Failed to create file");
            }
            // 파일에 데이터를 쓴다.
            FileOutputStream fileOutputStream = new FileOutputStream(uploadServerFile);
            fileOutputStream.write(file);
            fileOutputStream.close();
            return uploadServerFilePath;
        } catch (IOException e) {
            throw new RuntimeException("Failed to write file");
        }
    }

}

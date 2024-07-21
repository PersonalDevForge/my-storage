package org.c4marathon.assignment.folder.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class WriteFolderService {

    public String writeFolder(String email, String folderName, String parentFolderPath) {
        File file;
        if (parentFolderPath == null) {
            file = new File("src/main/resources/upload/" + email + "/" + folderName);
        } else {
            file = new File(parentFolderPath + "/" + folderName);
        }

        // 폴더가 이미 존재하는지 확인한다.
        if (file.exists()) {
            throw new IllegalArgumentException("Folder already exists");
        }

        // 폴더를 생성한다.
        if (!file.mkdirs()) {
            throw new IllegalArgumentException("Failed to create folder");
        }

        return file.getPath();
    }

}

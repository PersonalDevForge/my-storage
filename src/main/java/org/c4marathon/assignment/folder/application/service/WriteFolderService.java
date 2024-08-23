package org.c4marathon.assignment.folder.application.service;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.service.component.FileFactory;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class WriteFolderService {

    private final FileFactory fileFactory;

    public String writeFolder(String email, String folderName, String parentFolderPath) {
        File file;
        if (parentFolderPath == null) {
            file = fileFactory.createFile("src/main/resources/upload/" + email + "/" + folderName);
        } else {
            file = fileFactory.createFile(parentFolderPath + "/" + folderName);
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

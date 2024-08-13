package org.c4marathon.assignment.file.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.in.CopyFileUseCase;
import org.c4marathon.assignment.file.application.port.out.FileCommandPort;
import org.c4marathon.assignment.file.application.port.out.FileQueryPort;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CopyFileService implements CopyFileUseCase {

    private final FileSearchService fileSearchService;

    private final FileCommandPort fileCommandPort;

    private final FileQueryPort fileQueryPort;

    private void copyActualFile(File file, String folderPath, String uuidFileName, String type) {
        String newFilePath = folderPath + uuidFileName;
        try {
            Files.copy(Paths.get(file.getPath()), Paths.get(newFilePath + "." + type));
        } catch (java.io.IOException e) {
            throw new IllegalArgumentException("Failed to copy file");
        }
    }

    private String extractFileName(String fileName, String type) {
        if (type.isEmpty()) {
            return fileName;
        }
        return fileName.substring(0, fileName.length() - type.length() - 1);
    }

    private String extractPath(String path) {
        return path.substring(0, path.lastIndexOf('/') + 1);
    }

    private String makeCoypString(User user, String fileName, String type, Folder folder) {
        String copy = " - Copy";
        while (true) {
            Optional<File> mustNotExist = fileQueryPort.findByUserAndFileNameAndFolder(user, fileName + copy + "." + type, folder);
            if (mustNotExist.isPresent())
                copy += " - Copy";
            else
                break;
        }
        return copy;
    }

    @Override
    @Transactional
    public void copyFile(User user, Long originFileId) {
        File originFile = fileSearchService.getFile(user, originFileId);

        String type = originFile.getType();
        String uuidFileName = UUID.randomUUID().toString();
        Long size = originFile.getSize();
        Folder folder = originFile.getFolder();

        String copyString = makeCoypString(user, extractFileName(originFile.getFileName(), type), type, folder);
        copyActualFile(originFile, extractPath(originFile.getPath()), uuidFileName, type);
        String copyFileName = extractFileName(originFile.getFileName(), type) + copyString + "." + type;
        String copyPath = extractPath(originFile.getPath()) + uuidFileName + "." + type;
        File copiedFile = File.of(user, folder, copyPath, uuidFileName, copyFileName, type, size);
        fileCommandPort.save(copiedFile);
    }

}

package org.c4marathon.assignment.file.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.in.MoveFileUseCase;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.application.service.FolderSearchService;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MoveFileService implements MoveFileUseCase {

    private final FileSearchService fileSearchService;

    private final FolderSearchService folderSearchService;

    private void applyActualMove(String originPath, File file) {
        java.io.File originFile = new java.io.File(originPath);
        java.io.File destFile = new java.io.File(file.getPath());
        System.out.println("originFile: " + originFile);
        System.out.println("destFile: " + destFile);
        if (destFile.exists()) {
            throw new IllegalArgumentException("File already exists");
        } else if (!originFile.exists()) {
            throw new IllegalArgumentException("File not found");
        }
        if (!originFile.renameTo(destFile)) {
            throw new IllegalArgumentException("File move failed");
        }
    }

    @Override
    @Transactional
    public void moveFile(User user, Long fileId, Long folderId) {
        File file = fileSearchService.getFile(user, fileId);
        String originPath = file.getPath();
        if (folderId == null) {
            file.move(null);
            applyActualMove(originPath, file);
            return;
        }
        Folder destFolder = folderSearchService.findById(user, folderId);

        file.move(destFolder);
        applyActualMove(originPath, file);
    }

}

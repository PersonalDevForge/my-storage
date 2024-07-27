package org.c4marathon.assignment.folder.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.folder.application.port.in.MoveFolderUseCase;
import org.c4marathon.assignment.folder.application.port.out.FolderQueryPort;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.global.exception.customs.NotFoundException;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MoveFolderService implements MoveFolderUseCase {

    private final FolderSearchService folderSearchService;

    private final FolderQueryPort folderQueryPort;

    private final UpdatePathService updatePathService;

    private void moveActualFolder(Folder folder, String destPath) {
        java.io.File from = new java.io.File(folder.getPath());
        java.io.File to = new java.io.File(destPath);

        try {
            Files.move(from.toPath(), to.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to move folder", e);
        }
    }

    @Override
    @Transactional
    public void moveFolder(User user, Long folderId, Long destFolderId) {
        if (folderId == null) {
            throw new IllegalArgumentException("루트 폴더는 옮길 수 없습니다.");
        }
        Folder folder = folderSearchService.findById(user, folderId);

        List<Folder> childFolders = folderQueryPort.findByUserAndParentFolder(user, folder);
        for (Folder childFolder : childFolders) {
            moveFolder(user, childFolder.getId(), destFolderId);
        }

        String destPath = destFolderId == null ?
                "src/main/resources/upload/" + user.getEmail() :
                folderQueryPort.findByUserAndId(user, destFolderId)
                        .orElseThrow(() -> new NotFoundException("Folder not found"))
                        .getPath();
        moveActualFolder(folder, destPath + "/" + folder.getFolderName());
        folder.updatePath(destFolderId == null ? null : folderQueryPort.findByUserAndId(user, destFolderId)
                .orElseThrow(() -> new NotFoundException("Folder not found")), destPath);
        updatePathService.updatePathInternal(folder);
    }

}

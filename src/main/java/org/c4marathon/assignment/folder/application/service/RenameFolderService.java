package org.c4marathon.assignment.folder.application.service;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.folder.application.port.in.RenameFolderUseCase;
import org.c4marathon.assignment.folder.application.port.out.FolderCommandPort;
import org.c4marathon.assignment.folder.application.port.out.FolderQueryPort;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class RenameFolderService implements RenameFolderUseCase {

    private final FolderSearchService folderSearchService;

    private final FolderQueryPort folderQueryPort;

    private final FolderCommandPort folderCommandPort;

    private String renameActualFolder(Folder folder, String newFolderName) {
        File file = new File(folder.getPath());
        File existingFolder = new File(file.getParent() + File.separator + newFolderName);

        if (existingFolder.exists()) {
            throw new IllegalArgumentException("Folder already exists");
        }

        if (!file.renameTo(existingFolder)) {
            throw new IllegalArgumentException("Failed to create folder");
        }

        return existingFolder.getPath();
    }

    @Override
    public void renameFolder(User user, Long folderId, String newFolderName) {
        folderQueryPort.findByUserAndName(user, newFolderName).ifPresent(folder -> {
            throw new IllegalArgumentException("Folder already exists");
        });
        Folder folder = folderSearchService.findById(user, folderId);
        String newPath = renameActualFolder(folder, newFolderName);
        folder.rename(newFolderName, newPath);
        folderCommandPort.update(folder);
    }

}

package org.c4marathon.assignment.folder.application.service;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.out.FileCommandPort;
import org.c4marathon.assignment.file.application.service.DeleteFileService;
import org.c4marathon.assignment.file.application.service.FileSearchService;
import org.c4marathon.assignment.folder.application.port.in.DeleteFolderUseCase;
import org.c4marathon.assignment.folder.application.port.out.FolderCommandPort;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class DeleteFolderService implements DeleteFolderUseCase {

    private final FolderSearchService folderSearchService;

    private final FileSearchService fileSearchService;

    private final DeleteFileService deleteFileService;

    private final FolderCommandPort folderCommandPort;

    private void deleteActualFolder(Folder folder) {
        File file = new File(folder.getPath());

        if (!file.exists()) {
            return;
        }

        file.delete();
    }

    @Override
    public void deleteFolder(User user, Long folderId) {
        Folder folder = folderSearchService.findById(user, folderId);

        fileSearchService.getFileListByFolder(user, folderId).forEach(file -> {
            deleteFileService.deleteFile(user, file.getId());
        });

        folderSearchService.findAllSubElementsById(user, folderId).forEach(subFolder -> {
            deleteFolder(user, subFolder.getId());
        });

        deleteActualFolder(folder);
        folderCommandPort.delete(folder);
    }
}

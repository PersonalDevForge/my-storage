package org.c4marathon.assignment.folder.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.service.FileSearchService;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.application.port.in.UpdateSummaryUseCase;
import org.c4marathon.assignment.folder.application.port.out.FolderCommandPort;
import org.c4marathon.assignment.folder.application.port.out.FolderQueryPort;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateSummaryService implements UpdateSummaryUseCase {

    private final FolderSearchService folderSearchService;

    private final FolderQueryPort folderQueryPort;

    private final FolderCommandPort folderCommandPort;

    private final FileSearchService fileSearchService;

    /**
     * Update Summary data of Folder and its Parent Folders
     *
     * @param user   User
     * @param folderId FolderId
     */
    @Override
    @Transactional
    public void updateSummary(User user, Long folderId, LocalDateTime updatedAt) {
        Optional<Folder> existFolder = folderQueryPort.findByUserAndId(user, folderId);

        // if folder is null then folder is root folder
        if (existFolder.isEmpty()) {
            return;
        }
        Folder folder = existFolder.get();

        // Summary Initialization
        folder.initializeFolderSize();

        // Update Inner Folder Count and Size
        List<Folder> childFolders = folderSearchService.findAllSubElementsById(user, folder.getId());
        folder.updateInnerFolderCount((long) childFolders.size());
        for (Folder child : childFolders) {
            folder.addFolderSize(child.getFolderSize());
        }

        // Update Inner File Count and Size
        List<File> childFiles = fileSearchService.getFileListByFolder(user, folder.getId());
        folder.updateInnerFileCount((long) childFiles.size());
        for (File child : childFiles) {
            folder.addFolderSize(child.getSize());
        }

        // Update Folder UpdateAt
        folder.renewUpdatedAt(updatedAt);

        // Save Folder
        folderCommandPort.save(folder);

        // Recursively Update Parent Folder Summary
        updateSummary(user, folder.getParentFolder() == null ? null : folder.getParentFolder().getId(), updatedAt);
    }

}

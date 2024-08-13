package org.c4marathon.assignment.folder.application.service;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.folder.application.port.in.MakeFolderUseCase;
import org.c4marathon.assignment.folder.application.port.in.UpdateSummaryUseCase;
import org.c4marathon.assignment.folder.application.port.out.FolderCommandPort;
import org.c4marathon.assignment.folder.application.port.out.FolderQueryPort;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MakeFolderService implements MakeFolderUseCase {

    private final FolderSearchService folderSearchService;

    private final FolderQueryPort folderQueryPort;

    private final FolderCommandPort folderCommandPort;

    private final WriteFolderService writeFolderService;

    private final UpdateSummaryUseCase updateSummaryUseCase;

    @Override
    public Folder makeFolder(User user, String folderName, Long parentFolderId) {
        if (folderQueryPort.findByUserAndParentFolderIdAndFolderName(user, parentFolderId, folderName).isPresent()) {
            throw new IllegalArgumentException("Folder already exists");
        }
        Folder parentFolder;
        if (parentFolderId == null) {
            parentFolder = null;
        } else {
            parentFolder = folderSearchService.findById(user, parentFolderId);
        }
        String path = writeFolderService.writeFolder(user.getEmail(), folderName, parentFolder == null ? null : parentFolder.getPath());
        Folder folder = Folder.of(user, parentFolder, folderName, path, 0L, 0L, 0L);
        folder = folderCommandPort.save(folder);
        updateSummaryUseCase.updateSummary(user, parentFolderId, null);
        return folder;
    }

}

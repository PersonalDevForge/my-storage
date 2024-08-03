package org.c4marathon.assignment.folder.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.service.FileSearchService;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdatePathService {

    private final FolderSearchService folderSearchService;

    private final FileSearchService fileSearchService;

    @Transactional
    public void updatePathInternal(Folder folder) {
        List<Folder> childFolders = folderSearchService.findAllSubElementsById(folder.getUser(), folder.getId());
        for (Folder childFolder : childFolders) {
            childFolder.updatePath(folder, folder.getPath());
            updatePathInternal(childFolder);
        }
        List<File> files = fileSearchService.getFileListByFolder(folder.getUser(), folder.getId());
        for (File file : files) {
            file.updatePath();
        }
    }

}

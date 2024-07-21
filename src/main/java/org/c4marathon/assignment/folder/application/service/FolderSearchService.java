package org.c4marathon.assignment.folder.application.service;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.folder.application.port.in.SearchFolderUseCase;
import org.c4marathon.assignment.folder.application.port.out.FolderQueryPort;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.global.exception.customs.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderSearchService implements SearchFolderUseCase {

    private final FolderQueryPort folderQueryPort;

    @Override
    public Folder findById(Long folderId) {
        return folderQueryPort.findById(folderId).orElseThrow(() -> new NotFoundException("Folder not found"));
    }

    @Override
    public List<Folder> findAllSubElementsById(Long folderId) {
        return folderQueryPort.findByParentFolderId(folderId);
    }

    @Override
    public List<Folder> findAllSubElementsByPath(String path) {
        return folderQueryPort.findByPath(path);
    }
}

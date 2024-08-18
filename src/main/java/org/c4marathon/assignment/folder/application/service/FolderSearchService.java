package org.c4marathon.assignment.folder.application.service;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.folder.application.port.in.SearchFolderUseCase;
import org.c4marathon.assignment.folder.application.port.out.FolderQueryPort;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.global.exception.customs.NotFoundException;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderSearchService implements SearchFolderUseCase {

    private final FolderQueryPort folderQueryPort;

    @Override
    public Folder findById(User user, Long folderId) {
        return folderQueryPort.findByUserAndId(user, folderId).orElseThrow(() -> new NotFoundException("Folder not found"));
    }

    @Override
    public Page<Folder> findAllSubElementsByIdPageable(User user, Long folderId, int page, int size) {
        return folderQueryPort.findByUserAndParentFolderIdPageable(user, folderId, Pageable.ofSize(size).withPage(page));
    }

    @Override
    public List<Folder> findAllSubElementsById(User user, Long folderId) {
        return folderQueryPort.findByUserAndParentFolderId(user, folderId);
    }

    @Override
    public List<Folder> findAllSubElementsByPath(User user, String path) {
        Folder folder = folderQueryPort.findByUserAndPath(user, path).orElseThrow(() -> new NotFoundException("Folder not found"));
        return findAllSubElementsById(user, folder.getId());
    }
}

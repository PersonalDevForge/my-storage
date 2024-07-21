package org.c4marathon.assignment.folder.adapter.out;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.folder.application.port.out.FolderQueryPort;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.folder.infrastructure.repository.FolderRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FolderQueryAdapter implements FolderQueryPort {

    private final FolderRepository folderRepository;

    @Override
    public Optional<Folder> findById(Long folderId) {
        return folderRepository.findById(folderId);
    }

    @Override
    public List<Folder> findByParentFolder(Folder parentFolder) {
        return folderRepository.findByParentFolder(parentFolder);
    }

    @Override
    public List<Folder> findByParentFolderId(Long parentFolderId) {
        Optional<Folder> parentFolder = findById(parentFolderId);
        if (parentFolder.isPresent()) {
            return folderRepository.findByParentFolder(parentFolder.get());
        } else {
            return List.of();
        }
    }

    @Override
    public List<Folder> findByPath(String path) {
        return folderRepository.findByPath(path);
    }
}

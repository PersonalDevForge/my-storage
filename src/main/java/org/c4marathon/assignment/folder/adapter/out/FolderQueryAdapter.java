package org.c4marathon.assignment.folder.adapter.out;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.folder.application.port.out.FolderQueryPort;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.folder.infrastructure.repository.FolderRepository;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FolderQueryAdapter implements FolderQueryPort {

    private final FolderRepository folderRepository;

    @Override
    public Optional<Folder> findByUserAndId(User user, Long folderId) {
        return folderRepository.findByUserAndId(user, folderId);
    }

    @Override
    public List<Folder> findByUserAndParentFolder(User user, Folder parentFolder) {
        return folderRepository.findAllByUserAndParentFolder(user, parentFolder);
    }

    @Override
    public Optional<Folder> findByUserAndParentFolderIdAndFolderName(User user, Long parentFolderId, String folderName) {
        Optional<Folder> parentFolder = findByUserAndId(user, parentFolderId);
        if (parentFolder.isPresent()) {
            return folderRepository.findByUserAndParentFolderAndFolderName(user, parentFolder.get(), folderName);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Folder> findByUserAndParentFolderId(User user, Long parentFolderId) {
        if (parentFolderId == null) {
            return folderRepository.findAllByUserAndParentFolder(user, null);
        } else {
            Optional<Folder> parentFolder = findByUserAndId(user, parentFolderId);
            if (parentFolder.isEmpty()) {
                return List.of();
            }
            return folderRepository.findAllByUserAndParentFolder(user, parentFolder.get());
        }
    }

    @Override
    public Optional<Folder> findByUserAndPath(User user, String path) {
        return folderRepository.findByUserAndPath(user, path);
    }

    @Override
    public Optional<Folder> findByUserAndName(User user, String folderName) {
        return folderRepository.findByUserAndFolderName(user, folderName);
    }
}

package org.c4marathon.assignment.folder.infrastructure.repository;

import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Long> {

    Optional<Folder> findByUserAndId(User user, Long id);

    List<Folder> findByUserAndParentFolder(User user, Folder parentFolder);

    Optional<Folder> findByUserAndParentFolderAndFolderName(User user, Folder parentFolder, String folderName);

    Optional<Folder> findByUserAndPath(User user, String path);

    Optional<Folder> findByUserAndFolderName(User user, String folderName);

}

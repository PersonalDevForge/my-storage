package org.c4marathon.assignment.folder.infrastructure.repository;

import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Long> {

    Optional<Folder> findByUserAndId(User user, Long id);

    List<Folder> findAllByUserAndParentFolder(User user, Folder parentFolder);

    @Query("SELECT f FROM Folder f WHERE f.user = ?1 AND " +
            "(?2 IS NULL AND f.parentFolder IS NULL OR f.parentFolder.id = ?2)")
    Page<Folder> findByUserAndParentFolderIdPageable(User user, Long parentFolderId, Pageable pageable);

    Optional<Folder> findByUserAndParentFolderAndFolderName(User user, Folder parentFolder, String folderName);

    Optional<Folder> findByUserAndPath(User user, String path);

    Optional<Folder> findByUserAndFolderName(User user, String folderName);

    Optional<Folder> findByUserAndFolderNameAndParentFolder(User user, String folderName, Folder parentFolder);

}

package org.c4marathon.assignment.file.infrastructure.repository;

import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findAllByUser(User user);

    Optional<File> findByUserAndId(User user, Long id);

    Optional<File> findByUserAndFileNameAndFolder(User user, String fileName, Folder folder);

    Optional<File> findByPath(String path);

    List<File> findAllByUserAndFolder(User user, Folder folder);

    @Query("SELECT f FROM File f WHERE f.user = :user AND " +
            "(:folderId IS NULL AND f.folder IS NULL OR f.folder.id = :folderId) " +
            "ORDER BY f.id ASC")
    Page<File> findAllByUserAndFolderPageable(User user, Long folderId, Pageable pageable);
}

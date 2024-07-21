package org.c4marathon.assignment.file.infrastructure.repository;

import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findAllByUser(User user);

    Optional<File> findByUserAndFileName(User user, String fileName);

    List<File> findAllByUserAndFolder(User user, Folder folder);
}

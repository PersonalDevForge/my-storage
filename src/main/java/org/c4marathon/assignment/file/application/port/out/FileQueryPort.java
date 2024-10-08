package org.c4marathon.assignment.file.application.port.out;

import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FileQueryPort {

    List<File> findAllByUser(User user);

    Optional<File> findByUserAndId(User user, Long id);

    Optional<File> findByUserAndFileNameAndFolder(User user, String fileName, Folder folder);

    Optional<File> findByPath(String path);

    List<File> findAllByUserAndFolder(User user, Folder folder);

    Page<File> findAllByUserAndFolderPageable(User user, Long folderId, Pageable pageable);

    List<File> findFilesWithOffset(User user, Long folderId, int offset, int limit);
}

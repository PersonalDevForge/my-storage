package org.c4marathon.assignment.folder.application.port.out;

import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.user.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface FolderQueryPort {

    Optional<Folder> findByUserAndId(User user, Long folderId);

    List<Folder> findByUserAndParentFolder(User user, Folder parentFolder);

    Optional<Folder> findByUserAndParentFolderIdAndFolderName(User user, Long parentFolderId, String folderName);

    List<Folder> findByUserAndParentFolderId(User user, Long parentFolderId);

    Optional<Folder> findByUserAndPath(User user, String path);

    Optional<Folder> findByUserAndName(User user, String folderName);

}

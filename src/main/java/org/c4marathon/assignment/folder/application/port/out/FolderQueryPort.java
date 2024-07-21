package org.c4marathon.assignment.folder.application.port.out;

import org.c4marathon.assignment.folder.domain.entity.Folder;

import java.util.List;
import java.util.Optional;

public interface FolderQueryPort {

    Optional<Folder> findById(Long folderId);

    List<Folder> findByParentFolder(Folder parentFolder);

    List<Folder> findByParentFolderId(Long parentFolderId);

    List<Folder> findByPath(String path);

}

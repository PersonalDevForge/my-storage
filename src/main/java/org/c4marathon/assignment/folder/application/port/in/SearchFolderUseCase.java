package org.c4marathon.assignment.folder.application.port.in;

import org.c4marathon.assignment.folder.domain.entity.Folder;

import java.util.List;

public interface SearchFolderUseCase {

    Folder findById(Long folderId);

    List<Folder> findAllSubElementsById(Long folderId);

    List<Folder> findAllSubElementsByPath(String path);

}

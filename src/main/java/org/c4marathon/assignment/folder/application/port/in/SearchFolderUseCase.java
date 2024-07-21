package org.c4marathon.assignment.folder.application.port.in;

import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.user.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface SearchFolderUseCase {

    Folder findById(User user, Long folderId);

    List<Folder> findAllSubElementsById(User user, Long folderId);

    List<Folder> findAllSubElementsByPath(User user, String path);

}

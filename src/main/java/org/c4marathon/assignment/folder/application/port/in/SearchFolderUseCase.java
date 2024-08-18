package org.c4marathon.assignment.folder.application.port.in;

import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SearchFolderUseCase {

    Folder findById(User user, Long folderId);

    Page<Folder> findAllSubElementsByIdPageable(User user, Long folderId, int page, int size);

    List<Folder> findAllSubElementsById(User user, Long folderId);

    List<Folder> findAllSubElementsByPath(User user, String path);

}

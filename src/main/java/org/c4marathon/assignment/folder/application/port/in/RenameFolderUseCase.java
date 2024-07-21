package org.c4marathon.assignment.folder.application.port.in;

import org.c4marathon.assignment.user.domain.entity.User;

public interface RenameFolderUseCase {

    void renameFolder(User user, Long folderId, String newFolderName);

}

package org.c4marathon.assignment.folder.application.port.in;

import org.c4marathon.assignment.user.domain.entity.User;

public interface MoveFolderUseCase {

    void moveFolder(User user, Long folderId, Long destFolderId);

}

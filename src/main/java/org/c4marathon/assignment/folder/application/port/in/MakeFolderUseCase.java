package org.c4marathon.assignment.folder.application.port.in;

import org.c4marathon.assignment.user.domain.entity.User;

public interface MakeFolderUseCase {

    void makeFolder(User user, String folderName, Long parentFolderId);

}

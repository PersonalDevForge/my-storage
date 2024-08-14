package org.c4marathon.assignment.share.application.port.in;

import org.c4marathon.assignment.user.domain.entity.User;

public interface PublishShareFolderUseCase {

    String publishShareFolder(User user, Long folderId);

}

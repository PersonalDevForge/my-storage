package org.c4marathon.assignment.file.application.port.in;

import org.c4marathon.assignment.user.domain.entity.User;

public interface CopyFileUseCase {

    void copyFile(User user, Long originFileId);

}

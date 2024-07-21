package org.c4marathon.assignment.file.application.port.in;

import org.c4marathon.assignment.user.domain.entity.User;

public interface DeleteFileUseCase {

    void deleteFile(User user, Long fileId);

}

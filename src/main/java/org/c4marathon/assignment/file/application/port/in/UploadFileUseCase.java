package org.c4marathon.assignment.file.application.port.in;

import org.c4marathon.assignment.user.domain.entity.User;

public interface UploadFileUseCase {

    void uploadFile(User user, String fileName, Long folderId, byte[] file);

}

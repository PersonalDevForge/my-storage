package org.c4marathon.assignment.file.application.port.in;

public interface UploadFileUseCase {

    void uploadFile(String email, String fileName, Long folderId, byte[] file);

}

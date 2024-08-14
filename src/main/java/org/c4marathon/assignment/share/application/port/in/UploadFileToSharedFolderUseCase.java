package org.c4marathon.assignment.share.application.port.in;

public interface UploadFileToSharedFolderUseCase {

    void uploadFileToSharedFolder(String uuid, String fileName, byte[] file);

}

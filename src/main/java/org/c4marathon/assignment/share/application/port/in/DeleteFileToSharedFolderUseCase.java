package org.c4marathon.assignment.share.application.port.in;

public interface DeleteFileToSharedFolderUseCase {

    void deleteFileToSharedFolder(String uuid, Long fileId);

}

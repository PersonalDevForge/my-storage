package org.c4marathon.assignment.share.application.port.in;

public interface DownloadFileFromSharedFolderUseCase {

    String downloadFileFromSharedFolder(String uuid, Long fileId);

}

package org.c4marathon.assignment.thumbnail.application.port.in;

public interface DeleteThumbnailUseCase {

    void deleteThumbnail(Long fileId);

    void deleteAllThumbnailInFolder(Long folderId);

}

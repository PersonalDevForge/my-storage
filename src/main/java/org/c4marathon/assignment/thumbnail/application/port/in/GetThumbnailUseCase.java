package org.c4marathon.assignment.thumbnail.application.port.in;

import org.c4marathon.assignment.thumbnail.domain.entity.Thumbnail;

import java.util.List;

public interface GetThumbnailUseCase {

    Thumbnail getThumbnail(Long fileId);

    List<Thumbnail> getAllThumbnailInFolder(Long folderId);

}

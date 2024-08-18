package org.c4marathon.assignment.thumbnail.application.port.out;

import org.c4marathon.assignment.thumbnail.domain.entity.Thumbnail;

import java.util.List;
import java.util.Optional;

public interface ThumbnailQueryPort {

    Optional<Thumbnail> findById(Long id);

    Optional<Thumbnail> findByFileId(Long fileId);

    List<Thumbnail> findAllByFolderId(Long folderId);

}

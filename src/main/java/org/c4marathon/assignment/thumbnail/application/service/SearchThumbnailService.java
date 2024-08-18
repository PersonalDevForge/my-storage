package org.c4marathon.assignment.thumbnail.application.service;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.thumbnail.application.port.in.GetThumbnailUseCase;
import org.c4marathon.assignment.thumbnail.application.port.out.ThumbnailQueryPort;
import org.c4marathon.assignment.thumbnail.domain.entity.Thumbnail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchThumbnailService implements GetThumbnailUseCase {

    private final ThumbnailQueryPort thumbnailQueryPort;

    @Override
    public Thumbnail getThumbnail(Long fileId) {
        return thumbnailQueryPort.findByFileId(fileId).orElse(null);
    }

    @Override
    public List<Thumbnail> getAllThumbnailInFolder(Long folderId) {
        return thumbnailQueryPort.findAllByFolderId(folderId);
    }

}

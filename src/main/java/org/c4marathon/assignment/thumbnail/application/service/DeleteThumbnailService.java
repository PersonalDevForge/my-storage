package org.c4marathon.assignment.thumbnail.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.thumbnail.application.port.in.DeleteThumbnailUseCase;
import org.c4marathon.assignment.thumbnail.application.port.out.ThumbnailCommandPort;
import org.c4marathon.assignment.thumbnail.application.port.out.ThumbnailQueryPort;
import org.c4marathon.assignment.thumbnail.domain.entity.Thumbnail;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeleteThumbnailService implements DeleteThumbnailUseCase {

    private final ThumbnailQueryPort thumbnailQueryPort;

    private final ThumbnailCommandPort thumbnailCommandPort;

    private void deleteActualImage(Thumbnail thumbnail) {
        try {
            Files.delete(Path.of(thumbnail.getPath()));
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to delete thumbnail");
        }
    }

    @Override
    @Transactional
    public void deleteThumbnail(Long fileId) {
        Optional<Thumbnail> thumbnail = thumbnailQueryPort.findByFileId(fileId);

        if (thumbnail.isEmpty()) {
            return;
        }

        deleteActualImage(thumbnail.get());
        thumbnailCommandPort.delete(thumbnail.get());
    }

    @Override
    @Transactional
    public void deleteAllThumbnailInFolder(Long folderId) {
        List<Thumbnail> thumbnails = thumbnailQueryPort.findAllByFolderId(folderId);

        thumbnails.forEach(thumbnail -> {
            deleteActualImage(thumbnail);
            thumbnailCommandPort.delete(thumbnail);
        });
    }

}

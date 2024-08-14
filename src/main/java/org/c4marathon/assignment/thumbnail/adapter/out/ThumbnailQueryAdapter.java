package org.c4marathon.assignment.thumbnail.adapter.out;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.thumbnail.application.port.out.ThumbnailQueryPort;
import org.c4marathon.assignment.thumbnail.domain.entity.Thumbnail;
import org.c4marathon.assignment.thumbnail.infrastructure.repository.ThumbnailRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ThumbnailQueryAdapter implements ThumbnailQueryPort {

    private final ThumbnailRepository thumbnailRepository;

    @Override
    public Optional<Thumbnail> findById(Long id) {
        return thumbnailRepository.findById(id);
    }

    @Override
    public Optional<Thumbnail> findByFileId(Long fileId) {
        return thumbnailRepository.findByFileId(fileId);
    }

    @Override
    public List<Thumbnail> findAllByFolderId(Long folderId) {
        return thumbnailRepository.findAllByFolderId(folderId);
    }

}

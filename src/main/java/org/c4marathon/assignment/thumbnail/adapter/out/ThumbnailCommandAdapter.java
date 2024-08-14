package org.c4marathon.assignment.thumbnail.adapter.out;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.thumbnail.application.port.out.ThumbnailCommandPort;
import org.c4marathon.assignment.thumbnail.domain.entity.Thumbnail;
import org.c4marathon.assignment.thumbnail.infrastructure.repository.ThumbnailRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ThumbnailCommandAdapter implements ThumbnailCommandPort {

    private final ThumbnailRepository thumbnailRepository;

    @Override
    public Thumbnail save(Thumbnail thumbnail) {
        return thumbnailRepository.save(thumbnail);
    }

    @Override
    public void delete(Thumbnail thumbnail) {
        thumbnailRepository.delete(thumbnail);
    }

}

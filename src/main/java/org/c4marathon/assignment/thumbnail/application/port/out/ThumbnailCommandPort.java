package org.c4marathon.assignment.thumbnail.application.port.out;

import org.c4marathon.assignment.thumbnail.domain.entity.Thumbnail;

public interface ThumbnailCommandPort {

    Thumbnail save(Thumbnail thumbnail);

    void delete(Thumbnail thumbnail);

}

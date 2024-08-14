package org.c4marathon.assignment.thumbnail.application.port.in;

import org.c4marathon.assignment.file.domain.entity.File;

public interface GenerateThumbnailUseCase {

    String GenerateThumbnail(File file);

}

package org.c4marathon.assignment.file.application.port.out;

import org.c4marathon.assignment.file.domain.entity.File;

import java.util.Optional;

public interface FileCommandPort {

    File save(File file);

    File update(File file);

    void delete(File file);

}

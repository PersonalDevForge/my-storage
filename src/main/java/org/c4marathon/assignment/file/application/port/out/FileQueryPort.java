package org.c4marathon.assignment.file.application.port.out;

import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.user.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface FileQueryPort {

    List<File> findAllByUser(User user);

    Optional<File> findByUserAndFilename(User user, String filename);

}

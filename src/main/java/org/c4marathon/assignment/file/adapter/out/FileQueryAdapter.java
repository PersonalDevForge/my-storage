package org.c4marathon.assignment.file.adapter.out;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.out.FileQueryPort;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.file.infrastructure.repository.FileRepository;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FileQueryAdapter implements FileQueryPort {

    private final FileRepository fileRepository;

    @Override
    public List<File> findAllByUser(User user) {
        return fileRepository.findAllByUser(user);
    }

}

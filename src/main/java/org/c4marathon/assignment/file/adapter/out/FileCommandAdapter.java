package org.c4marathon.assignment.file.adapter.out;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.out.FileCommandPort;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.file.infrastructure.repository.FileRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FileCommandAdapter implements FileCommandPort {

    private final FileRepository fileRepository;

    @Override
    public File save(File file) {
        return fileRepository.save(file);
    }

    @Override
    public File update(File file) {
        return fileRepository.save(file);
    }

    @Override
    public void delete(File file) {
        fileRepository.delete(file);
    }
}

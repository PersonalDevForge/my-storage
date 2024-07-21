package org.c4marathon.assignment.file.adapter.out;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.out.FileQueryPort;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.file.infrastructure.repository.FileRepository;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FileQueryAdapter implements FileQueryPort {

    private final FileRepository fileRepository;

    @Override
    public List<File> findAllByUser(User user) {
        return fileRepository.findAllByUser(user);
    }

    @Override
    public Optional<File> findByUserAndId(User user, Long id) {
        return fileRepository.findByUserAndId(user, id);
    }

    @Override
    public Optional<File> findByUserAndFileNameAndFolder(User user, String fileName, Folder folder) {
        return fileRepository.findByUserAndFileNameAndFolder(user, fileName, folder);
    }

    @Override
    public Optional<File> findByPath(String path) {
        return fileRepository.findByPath(path);
    }

    @Override
    public List<File> findAllByUserAndFolder(User user, Folder folder) {
        return fileRepository.findAllByUserAndFolder(user, folder);
    }

}

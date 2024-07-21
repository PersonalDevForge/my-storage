package org.c4marathon.assignment.folder.adapter.out;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.folder.application.port.out.FolderCommandPort;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.folder.infrastructure.repository.FolderRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FolderCommandAdapter implements FolderCommandPort {

    private final FolderRepository folderRepository;

    @Override
    public Folder save(Folder folder) {
        return folderRepository.save(folder);
    }

    @Override
    public Folder update(Folder folder) {
        return folderRepository.save(folder);
    }

    @Override
    public void delete(Folder folder) {
        folderRepository.delete(folder);
    }
}

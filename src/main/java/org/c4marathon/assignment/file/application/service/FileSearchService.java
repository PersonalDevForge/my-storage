package org.c4marathon.assignment.file.application.service;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.in.GetFileListUseCase;
import org.c4marathon.assignment.file.application.port.out.FileQueryPort;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.application.service.FolderSearchService;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.global.exception.customs.NotFoundException;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileSearchService implements GetFileListUseCase {

    private final FileQueryPort fileQueryPort;

    private final FolderSearchService folderSearchService;

    @Override
    public List<File> getFileList(User user) {
        return fileQueryPort.findAllByUser(user);
    }

    @Override
    public File getFile(User user, Long fileId) {
        return fileQueryPort.findByUserAndId(user, fileId)
                .orElseThrow(() -> new NotFoundException("File not found"));
    }

    @Override
    public List<File> getFileListByFolder(User user, Long folderId) {
        Folder folder = folderSearchService.findById(user, folderId);
        return fileQueryPort.findAllByUserAndFolder(user, folder);
    }

}

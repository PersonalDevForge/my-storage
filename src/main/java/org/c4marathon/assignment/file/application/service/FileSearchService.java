package org.c4marathon.assignment.file.application.service;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.in.GetFileListUseCase;
import org.c4marathon.assignment.file.application.port.out.FileQueryPort;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.application.service.FolderSearchService;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.global.exception.customs.NotFoundException;
import org.c4marathon.assignment.user.application.service.UserSearchService;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileSearchService implements GetFileListUseCase {

    private final FileQueryPort fileQueryPort;

    private final FolderSearchService folderSearchService;

    private final UserSearchService userSearchService;

    @Override
    public List<File> getFileList(String email) {
        User user = userSearchService.getUserByEmail(email);
        return fileQueryPort.findAllByUser(user);
    }

    @Override
    public File getFile(String email, String filename) {
        return fileQueryPort.findByUserAndFilename(userSearchService.getUserByEmail(email), filename)
                .orElseThrow(() -> new NotFoundException("File not found"));
    }

    @Override
    public List<File> getFileListByFolder(String email, Long folderId) {
        User user = userSearchService.getUserByEmail(email);
        Folder folder = folderSearchService.findById(folderId);
        return fileQueryPort.findAllByUserAndFolder(user, folder);
    }

}

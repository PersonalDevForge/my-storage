package org.c4marathon.assignment.folder.application.service;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.service.FileSearchService;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.adapter.in.dto.FolderSummaryResponseDto;
import org.c4marathon.assignment.folder.application.port.in.GetFolderSummaryUseCase;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SummaryService implements GetFolderSummaryUseCase {

    private final FolderSearchService folderSearchService;

    private final FileSearchService fileSearchService;

    @Override
    public FolderSummaryResponseDto getFolderSummary(User user, Long folderId) {
        Folder folder = folderSearchService.findById(user, folderId);
        List<File> files = fileSearchService.getFileListByFolder(user, folderId);
        return null;
    }

}

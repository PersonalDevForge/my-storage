package org.c4marathon.assignment.folder.application.service;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.folder.adapter.in.dto.FolderSummaryResponseDto;
import org.c4marathon.assignment.folder.application.port.in.GetFolderSummaryUseCase;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummaryService implements GetFolderSummaryUseCase {

    private final FolderSearchService folderSearchService;

    @Override
    public FolderSummaryResponseDto getFolderSummary(User user, String folderId) {
        Folder folder = folderSearchService.findById(user, Long.parseLong(folderId));
        return null;
    }

}

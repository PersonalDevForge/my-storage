package org.c4marathon.assignment.folder.application.service;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.service.FileSearchService;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.adapter.in.dto.FolderSummaryResponseDto;
import org.c4marathon.assignment.folder.application.port.in.GetFolderSummaryUseCase;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SummaryService implements GetFolderSummaryUseCase {

    private final FolderSearchService folderSearchService;

    private final FileSearchService fileSearchService;

    @Value("${max.user.file.size}")
    private Long MAX_USER_FILE_SIZE;

    private FolderSummaryResponseDto getRootFolderSummary(User user) {
        List<Folder> folders = folderSearchService.findAllSubElementsById(user, null);
        List<File> files = fileSearchService.getFileListByFolder(user, null);

        LocalDateTime updatedAt = null;
        Long size = 0L;
        for (Folder folder : folders) {
            size += folder.getFolderSize();
            if (updatedAt == null || folder.getUpdatedAt().isAfter(updatedAt)) {
                updatedAt = folder.getUpdatedAt();
            }
        }
        for (File file : files) {
            size += file.getSize();
            if (updatedAt == null || file.getUpdatedAt().isAfter(updatedAt)) {
                updatedAt = file.getUpdatedAt();
            }
        }

        return FolderSummaryResponseDto.of(null, (long)folders.size(), (long)files.size(), size, MAX_USER_FILE_SIZE - size, null, updatedAt);
    }

    @Override
    public FolderSummaryResponseDto getFolderSummary(User user, Long folderId) {
        if (folderId == null) {
            return getRootFolderSummary(user);
        }
        Folder folder = folderSearchService.findById(user, folderId);
        return FolderSummaryResponseDto.of(folder.getId(), folder.getInnerFolderCount(), folder.getInnerFileCount(), folder.getFolderSize(), folder.getCreatedAt(), folder.getUpdatedAt());
    }

}

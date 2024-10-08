package org.c4marathon.assignment.file.application.service;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.in.DeleteFileUseCase;
import org.c4marathon.assignment.file.application.port.out.FileCommandPort;
import org.c4marathon.assignment.file.application.service.component.FileFactory;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.application.port.in.UpdateSummaryUseCase;
import org.c4marathon.assignment.thumbnail.application.port.in.DeleteThumbnailUseCase;
import org.c4marathon.assignment.user.application.port.in.AddUsageUseCase;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeleteFileService implements DeleteFileUseCase {

    private final FileSearchService fileSearchService;

    private final FileCommandPort fileCommandPort;

    private final UpdateSummaryUseCase updateSummaryUseCase;

    private final AddUsageUseCase addUsageUseCase;

    private final DeleteThumbnailUseCase deleteThumbnailUseCase;

    private final FileFactory fileFactory;

    private void removeFromDisk(File file) {
        java.io.File diskFile = fileFactory.createFile(file.getPath());

        if (!diskFile.delete()) {
            throw new RuntimeException("Failed to delete file from disk");
        }
    }

    @Override
    public void deleteFile(User user, Long fileId) {
        File file = fileSearchService.getFile(user, fileId);

        Long fileSize = file.getSize();
        removeFromDisk(file);
        Long folderId = file.getFolder() == null ? null : file.getFolder().getId();
        deleteThumbnailUseCase.deleteThumbnail(fileId);
        fileCommandPort.delete(file);
        updateSummaryUseCase.updateSummary(user, folderId, LocalDateTime.now());
        addUsageUseCase.addUsageUseCase(user.getId(), -fileSize);
    }

}

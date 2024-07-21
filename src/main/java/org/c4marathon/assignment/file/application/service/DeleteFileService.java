package org.c4marathon.assignment.file.application.service;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.in.DeleteFileUseCase;
import org.c4marathon.assignment.file.application.port.out.FileCommandPort;
import org.c4marathon.assignment.file.domain.entity.File;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteFileService implements DeleteFileUseCase {

    private final FileSearchService fileSearchService;

    private final FileCommandPort fileCommandPort;

    private void removeFromDisk(File file) {
        java.io.File diskFile = new java.io.File(file.getPath());

        if (!diskFile.delete()) {
            throw new RuntimeException("Failed to delete file from disk");
        }
    }

    @Override
    public void deleteFile(String email, String filename) {
        File file = fileSearchService.getFile(email, filename);

        removeFromDisk(file);
        fileCommandPort.delete(file);
    }

}

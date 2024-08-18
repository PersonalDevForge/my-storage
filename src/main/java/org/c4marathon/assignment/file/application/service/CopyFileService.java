package org.c4marathon.assignment.file.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.in.CopyFileUseCase;
import org.c4marathon.assignment.file.application.port.out.FileCommandPort;
import org.c4marathon.assignment.file.application.port.out.FileQueryPort;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.application.port.in.UpdateSummaryUseCase;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.thumbnail.application.port.in.GenerateThumbnailUseCase;
import org.c4marathon.assignment.user.application.port.in.AddUsageUseCase;
import org.c4marathon.assignment.user.application.port.in.GetUserStorageUseCase;
import org.c4marathon.assignment.user.domain.entity.User;
import org.c4marathon.assignment.user.domain.entity.UserStorage;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.c4marathon.assignment.user.domain.entity.UserStorage.isStorageCapacityExceeded;

@Service
@RequiredArgsConstructor
public class CopyFileService implements CopyFileUseCase {

    private final FileSearchService fileSearchService;

    private final FileCommandPort fileCommandPort;

    private final FileQueryPort fileQueryPort;

    private final UpdateSummaryUseCase updateSummaryUseCase;

    private final GetUserStorageUseCase getUserStorageUseCase;

    private final AddUsageUseCase addUsageUseCase;

    private final GenerateThumbnailUseCase generateThumbnailUseCase;

    private void copyActualFile(File file, String folderPath, String uuidFileName, String type) {
        String newFilePath = folderPath + uuidFileName;
        try {
            Files.copy(Paths.get(file.getPath()), Paths.get(newFilePath + "." + type));
        } catch (java.io.IOException e) {
            throw new IllegalArgumentException("Failed to copy file");
        }
    }

    private String extractFileName(String fileName, String type) {
        if (type.isEmpty()) {
            return fileName;
        }
        return fileName.substring(0, fileName.length() - type.length() - 1);
    }

    private String extractPath(String path) {
        return path.substring(0, path.lastIndexOf('/') + 1);
    }

    private String makeCoypString(User user, String fileName, String type, Folder folder) {
        String copy = " - Copy";
        while (true) {
            Optional<File> mustNotExist = fileQueryPort.findByUserAndFileNameAndFolder(user, fileName + copy + "." + type, folder);
            if (mustNotExist.isPresent())
                copy += " - Copy";
            else
                break;
        }
        return copy;
    }

    private Boolean isImage(String type) {
        return type.equals("jpg") || type.equals("png") || type.equals("jpeg") || type.equals("gif") || type.equals("bmp") || type.equals("webp") || type.equals("ico");
    }

    @Override
    @Transactional
    public void copyFile(User user, Long originFileId) {
        File originFile = fileSearchService.getFile(user, originFileId);

        // check if storage capacity exceeded
        UserStorage userStorage = getUserStorageUseCase.getUserStorage(user.getId());
        if (isStorageCapacityExceeded(userStorage, originFile.getSize())) {
            throw new IllegalArgumentException("Storage capacity exceeded");
        }

        String type = originFile.getType();
        String uuidFileName = UUID.randomUUID().toString();
        Long size = originFile.getSize();
        Folder folder = originFile.getFolder();

        String copyString = makeCoypString(user, extractFileName(originFile.getFileName(), type), type, folder);
        copyActualFile(originFile, extractPath(originFile.getPath()), uuidFileName, type);
        String copyFileName = extractFileName(originFile.getFileName(), type) + copyString + "." + type;
        String copyPath = extractPath(originFile.getPath()) + uuidFileName + "." + type;
        File copiedFile = File.of(user, folder, copyPath, uuidFileName, copyFileName, type, size);
        fileCommandPort.save(copiedFile);
        updateSummaryUseCase.updateSummary(user, folder == null ? null : folder.getId(), LocalDateTime.now());
        addUsageUseCase.addUsageUseCase(user.getId(), size);
        // 이미지라면 썸네일을 생성한다.
        if (isImage(type)) {
            generateThumbnailUseCase.generateThumbnail(copiedFile);
        }
    }

}

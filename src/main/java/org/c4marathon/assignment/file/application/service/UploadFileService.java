package org.c4marathon.assignment.file.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.in.UploadFileUseCase;
import org.c4marathon.assignment.file.application.port.out.FileCommandPort;
import org.c4marathon.assignment.file.application.port.out.FileQueryPort;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.application.port.in.UpdateSummaryUseCase;
import org.c4marathon.assignment.folder.application.service.FolderSearchService;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.thumbnail.application.port.in.GenerateThumbnailUseCase;
import org.c4marathon.assignment.user.application.port.in.AddUsageUseCase;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadFileService implements UploadFileUseCase {

    private final FileQueryPort fileQueryPort;

    private final FileCommandPort fileCommandPort;

    private final WriteFileService writeFileService;

    private final FolderSearchService folderSearchService;

    private final UpdateSummaryUseCase updateSummaryUseCase;

    private final AddUsageUseCase addUsageUseCase;

    private final GenerateThumbnailUseCase generateThumbnailUseCase;

    private String extractExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            throw new IllegalArgumentException("Invalid file name: no extension found");
        }
        return fileName.substring(lastDotIndex + 1).toLowerCase();
    }

    private String makeDefaultFilePath(String email) {
        return "src/main/resources/upload/" + email;
    }

    private Boolean isImage(String type) {
        return type.equals("jpg") || type.equals("png") || type.equals("jpeg") || type.equals("gif") || type.equals("bmp") || type.equals("webp") || type.equals("ico");
    }

    @Override
    @Transactional
    public void uploadFile(User user, String fileName, Long folderId, byte[] file) {
        String type = extractExtension(fileName);
        String uuidFileName = UUID.randomUUID().toString();
        Long size = (long) file.length;
        Folder folder = folderId == null ? null : folderSearchService.findById(user, folderId);

        // 중복 파일을 체크한다.
        if (fileQueryPort.findByUserAndFileNameAndFolder(user, fileName, folder).isPresent()) {
            throw new IllegalArgumentException("File already exists");
        }

        // 파일을 저장하고 저장된 파일의 경로를 반환한다.
        String uploadPath = writeFileService.writeFile(folder != null ? folder.getPath() : makeDefaultFilePath(user.getEmail()), uuidFileName, type, file);

        // 메타 정보를 저장한다.
        File metaData = File.of(user, folder, uploadPath, uuidFileName, fileName, type, size);
        metaData = fileCommandPort.save(metaData);

        // 폴더의 요약 정보를 업데이트한다.
        updateSummaryUseCase.updateSummary(user, folderId, LocalDateTime.now());

        // 사용량을 업데이트한다.
        addUsageUseCase.AddUsageUseCase(user.getId(), size);

        // 이미지라면 썸네일을 생성한다.
        if (isImage(type)) {
            generateThumbnailUseCase.generateThumbnail(metaData);
        }
    }

}

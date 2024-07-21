package org.c4marathon.assignment.file.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.in.UploadFileUseCase;
import org.c4marathon.assignment.file.application.port.out.FileCommandPort;
import org.c4marathon.assignment.file.application.port.out.FileQueryPort;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.application.service.FolderSearchService;
import org.c4marathon.assignment.folder.domain.entity.Folder;
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

    private String extractExtension(String fileName) {
        return fileName.substring(fileName.indexOf('.') + 1);
    }

    @Override
    @Transactional
    public void uploadFile(User user, String fileName, Long folderId, byte[] file) {
        String type = extractExtension(fileName);
        String uuidFileName = UUID.randomUUID().toString();
        Long size = (long) file.length;
        Folder folder = folderSearchService.findById(user, folderId);

        // 중복 파일을 체크한다.
        if (fileQueryPort.findByUserAndFileNameAndFolder(user, fileName, folder).isPresent()) {
            throw new IllegalArgumentException("File already exists");
        }

        // 파일을 저장하고 저장된 파일의 경로를 반환한다.
        String uploadPath = writeFileService.writeFile(folder.getPath(), uuidFileName, type, file);

        // 메타 정보를 저장한다.
        File metaData = File.of(user, folder, uploadPath, uuidFileName, fileName, type, size, LocalDateTime.now());
        fileCommandPort.save(metaData);
    }

}

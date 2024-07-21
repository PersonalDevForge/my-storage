package org.c4marathon.assignment.file.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.in.UploadFileUseCase;
import org.c4marathon.assignment.file.application.port.out.FileCommandPort;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.user.application.service.UserSearchService;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadFileService implements UploadFileUseCase {

    private final UserSearchService userSearchService;

    private final FileCommandPort fileCommandPort;

    private final WriteFileService writeFileService;

    private Pair<String, String> extractFileNameAndExtension(String fileName) {
        String name = fileName.substring(0, fileName.indexOf('.'));
        String extension = fileName.substring(fileName.indexOf('.') + 1);
        return Pair.of(name, extension);
    }

    @Override
    @Transactional
    public void uploadFile(String email, String fileName, byte[] file) {
        User fileOwner = userSearchService.getUserByEmail(email);
        Pair<String, String> nameAndExtension = extractFileNameAndExtension(fileName);
        String originalFileName = nameAndExtension.getFirst();
        String type = nameAndExtension.getSecond();
        String uuidFileName = UUID.randomUUID().toString();
        Long size = (long) file.length;

        // 파일을 저장하고 저장된 파일의 경로를 반환한다.
        String uploadPath = writeFileService.writeFile(email, uuidFileName, type, file);

        // 메타 정보를 저장한다.
        File metaData = File.of(fileOwner, uploadPath, uuidFileName, originalFileName, type, size, LocalDateTime.now());
        fileCommandPort.save(metaData);
    }

}

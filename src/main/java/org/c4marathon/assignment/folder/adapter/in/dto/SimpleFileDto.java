package org.c4marathon.assignment.folder.adapter.in.dto;

import lombok.Builder;
import lombok.Data;
import org.c4marathon.assignment.file.domain.entity.File;

import java.time.LocalDateTime;

@Data
public class SimpleFileDto {

    private final Long id;

    private final String fileName;

    private final String fileType;

    private final Long fileSize;

    private final Long folderId;

    private final String folderName;

    private final LocalDateTime createdAt;

    @Builder
    public SimpleFileDto(Long id, String fileName, String fileType, Long fileSize, Long folderId, String folderName, LocalDateTime createdAt) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.folderId = folderId;
        this.folderName = folderName;
        this.createdAt = createdAt;
    }

    public static SimpleFileDto from(File file) {
        return SimpleFileDto.builder()
                .id(file.getId())
                .fileName(file.getFileName())
                .fileType(file.getType())
                .fileSize(file.getSize())
                .folderId(file.getFolder().getId())
                .folderName(file.getFolder().getFolderName())
                .createdAt(file.getCreatedAt())
                .build();
    }

}

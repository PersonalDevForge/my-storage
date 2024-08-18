package org.c4marathon.assignment.folder.adapter.in.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String thumbnailUrl;

    private final LocalDateTime createdAt;

    @Builder
    public SimpleFileDto(Long id, String fileName, String fileType, Long fileSize, Long folderId, String folderName, String thumbnailUrl, LocalDateTime createdAt) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.folderId = folderId;
        this.folderName = folderName;
        this.thumbnailUrl = thumbnailUrl;
        this.createdAt = createdAt;
    }

    public static SimpleFileDto from(File file) {
        return SimpleFileDto.builder()
                .id(file.getId())
                .fileName(file.getFileName())
                .fileType(file.getType())
                .fileSize(file.getSize())
                .folderId(file.getFolder() != null ? file.getFolder().getId() : null)
                .folderName(file.getFolder() != null ? file.getFolder().getFolderName() : null)
                .thumbnailUrl(file.getThumbnail() != null ? file.getThumbnail().getThumbnailUrl() : null)
                .createdAt(file.getCreatedAt())
                .build();
    }

}

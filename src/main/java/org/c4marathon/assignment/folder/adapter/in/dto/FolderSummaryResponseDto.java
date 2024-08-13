package org.c4marathon.assignment.folder.adapter.in.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FolderSummaryResponseDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Long id;

    private final Long totalFolders;

    private final Long totalFiles;

    private final Long totalSize;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Long remainingSize;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public static FolderSummaryResponseDto of(Long id, Long totalFolders, Long totalFiles, Long totalSize, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new FolderSummaryResponseDto(id, totalFolders, totalFiles, totalSize, null, createdAt, updatedAt);
    }

    public static FolderSummaryResponseDto of(Long id, Long totalFolders, Long totalFiles, Long totalSize, Long remainingSize, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new FolderSummaryResponseDto(id, totalFolders, totalFiles, totalSize, remainingSize, createdAt updatedAt);
    }

}

package org.c4marathon.assignment.folder.adapter.in.dto;

import lombok.Data;

@Data
public class FolderSummaryResponseDto {

    private final Long id;

    private final Long totalFolders;

    private final Long totalFiles;

    private final Long totalSize;

}

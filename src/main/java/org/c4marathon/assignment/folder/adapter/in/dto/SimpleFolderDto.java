package org.c4marathon.assignment.folder.adapter.in.dto;

import lombok.Builder;
import lombok.Data;
import org.c4marathon.assignment.folder.domain.entity.Folder;

@Data
public class SimpleFolderDto {

    private final Long id;

    private final String folderName;

    private final Long parentFolderId;

    private final String parentFolderName;

    private final String path;

    @Builder
    public SimpleFolderDto(Long id, String folderName, Long parentFolderId, String parentFolderName, String path) {
        this.id = id;
        this.folderName = folderName;
        this.parentFolderId = parentFolderId;
        this.parentFolderName = parentFolderName;
        this.path = path;
    }

    public static SimpleFolderDto from(Folder folder) {
        return SimpleFolderDto.builder()
                .id(folder.getId())
                .folderName(folder.getFolderName())
                .parentFolderId(folder.getParentFolder() == null ? null : folder.getParentFolder().getId())
                .parentFolderName(folder.getParentFolder() == null ? null : folder.getParentFolder().getFolderName())
                .path(folder.getPath())
                .build();
    }

}

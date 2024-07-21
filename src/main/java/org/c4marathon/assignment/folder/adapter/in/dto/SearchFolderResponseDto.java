package org.c4marathon.assignment.folder.adapter.in.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(force = true)
public class SearchFolderResponseDto {

    private final List<SimpleFolderDto> folders;

    private final List<SimpleFileDto> files;

    private SearchFolderResponseDto(List<SimpleFolderDto> folders, List<SimpleFileDto> files) {
        this.folders = folders;
        this.files = files;
    }

    public static SearchFolderResponseDto from(List<SimpleFolderDto> folders, List<SimpleFileDto> files) {
        return new SearchFolderResponseDto(folders, files);
    }

}

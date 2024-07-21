package org.c4marathon.assignment.folder.adapter.in.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.domain.entity.Folder;

import java.util.List;

@Getter
@NoArgsConstructor(force = true)
public class SearchFolderResponseDto {

    private final List<Folder> folders;

    private final List<File> files;

    private SearchFolderResponseDto(List<Folder> folders, List<File> files) {
        this.folders = folders;
        this.files = files;
    }

    public static SearchFolderResponseDto from(List<Folder> folders, List<File> files) {
        return new SearchFolderResponseDto(folders, files);
    }

}

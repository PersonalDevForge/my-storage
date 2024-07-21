package org.c4marathon.assignment.folder.adapter.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.in.GetFileListUseCase;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.adapter.in.dto.SearchFolderResponseDto;
import org.c4marathon.assignment.folder.application.port.in.SearchFolderUseCase;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.global.response.ApiResponse;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/folders")
public class SearchFolderController {

    private final SearchFolderUseCase searchFolderUseCase;

    private final GetFileListUseCase getFileListUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<SearchFolderResponseDto>> searchFolderById(@NotBlank @Param("email") String email,
                                                                                 @Positive @Param("folderId") Long folderId) {
        List<Folder> folders = searchFolderUseCase.findAllSubElementsById(folderId);
        List<File> files = getFileListUseCase.getFileListByFolder(email, folderId);

        SearchFolderResponseDto response = SearchFolderResponseDto.from(folders, files);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

}

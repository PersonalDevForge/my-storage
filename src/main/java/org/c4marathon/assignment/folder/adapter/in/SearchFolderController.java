package org.c4marathon.assignment.folder.adapter.in;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.in.GetFileListUseCase;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.adapter.in.dto.SearchFolderResponseDto;
import org.c4marathon.assignment.folder.adapter.in.dto.SimpleFileDto;
import org.c4marathon.assignment.folder.adapter.in.dto.SimpleFolderDto;
import org.c4marathon.assignment.folder.application.port.in.SearchFolderUseCase;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.global.response.ApiResponse;
import org.c4marathon.assignment.user.application.service.UserSearchService;
import org.c4marathon.assignment.user.domain.entity.User;
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

    private final UserSearchService userSearchService;

    private final SearchFolderUseCase searchFolderUseCase;

    private final GetFileListUseCase getFileListUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<SearchFolderResponseDto>> searchFolderById(@NotBlank @Param("email") String email,
                                                                                 @Nullable @Param("folderId") Long folderId) {
        User user = userSearchService.getUserByEmail(email);
        List<Folder> folders = searchFolderUseCase.findAllSubElementsById(user, folderId);
        List<File> files = getFileListUseCase.getFileListByFolder(user, folderId);

        List<SimpleFolderDto> folderDtos = folders.stream().map(SimpleFolderDto::from).toList();
        List<SimpleFileDto> fileDtos = files.stream().map(SimpleFileDto::from).toList();
        SearchFolderResponseDto response = SearchFolderResponseDto.from(folderDtos, fileDtos);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

}

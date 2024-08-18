package org.c4marathon.assignment.folder.adapter.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/folders")
public class SearchFolderController {

    private final UserSearchService userSearchService;

    private final SearchFolderUseCase searchFolderUseCase;

    private final GetFileListUseCase getFileListUseCase;

    private final Integer PAGE_SIZE = 100;

    @GetMapping
    public ResponseEntity<ApiResponse<SearchFolderResponseDto>> searchFolderById(@NotBlank @Param("email") String email,
                                                                                 @NotNull @Param("folderId") Long folderId,
                                                                                 @NotNull @Param("page") Integer page) {
        User user = userSearchService.getUserByEmail(email);
        Page<Folder> folders = searchFolderUseCase.findAllSubElementsByIdPageable(user, folderId, page, PAGE_SIZE);
        int fileSize = PAGE_SIZE - folders.getNumberOfElements();
        int filePage = (fileSize != PAGE_SIZE) ? 0 : page - folders.getTotalPages();
        List<File> files = new ArrayList<>();
        if (fileSize > 0) {
            int offset = PAGE_SIZE * filePage;
            if (folders.getNumberOfElements() == 0) {
                offset += (int) ((folders.getTotalPages() * PAGE_SIZE) - folders.getTotalElements());
            }
            int limit = PAGE_SIZE - folders.getNumberOfElements();
            files = getFileListUseCase.getFileListByFolderPageable(user, folderId, offset, limit);
        }

        List<SimpleFolderDto> folderDtos = folders.get().map(SimpleFolderDto::from).toList();
        List<SimpleFileDto> fileDtos = files.stream().map(SimpleFileDto::from).toList();
        SearchFolderResponseDto response = SearchFolderResponseDto.from(folderDtos, fileDtos);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

}

package org.c4marathon.assignment.folder.adapter.in;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.folder.adapter.in.dto.FolderSummaryResponseDto;
import org.c4marathon.assignment.folder.application.port.in.GetFolderSummaryUseCase;
import org.c4marathon.assignment.global.response.ApiResponse;
import org.c4marathon.assignment.user.application.port.in.GetUserProfileUseCase;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/folders/summary")
public class GetFolderSummaryController {

    private final GetFolderSummaryUseCase getFolderSummaryUseCase;

    private final GetUserProfileUseCase getUserProfileUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<FolderSummaryResponseDto>> getSummary(@Param("email") String email,
                                                                            @Param("folderId") Long folderId) {
        User user = getUserProfileUseCase.getUserByEmail(email);
        FolderSummaryResponseDto folderSummaryResponseDto = getFolderSummaryUseCase.getFolderSummary(user, folderId);
        return ResponseEntity.ok(ApiResponse.success(folderSummaryResponseDto));
    }

}

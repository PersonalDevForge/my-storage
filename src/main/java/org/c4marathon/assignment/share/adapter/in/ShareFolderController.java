package org.c4marathon.assignment.share.adapter.in;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.global.response.ApiResponse;
import org.c4marathon.assignment.share.adapter.in.dto.PublishShareFolderResponseDto;
import org.c4marathon.assignment.share.application.port.in.PublishShareFolderUseCase;
import org.c4marathon.assignment.user.application.port.in.GetUserProfileUseCase;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/share/folders")
public class ShareFolderController {

    private final PublishShareFolderUseCase publishShareFolderUseCase;

    private final GetUserProfileUseCase getUserProfileUseCase;

    @PostMapping("{folderId}/publish")
    public ResponseEntity<ApiResponse<PublishShareFolderResponseDto>> publishShareFolder(@NotEmpty @Param("email") String email,
                                                                                         @NotNull @PathVariable("folderId") Long folderId) {
        User user = getUserProfileUseCase.getUserByEmail(email);
        String shareId = publishShareFolderUseCase.publishShareFolder(user, folderId);
        return ResponseEntity.ok(ApiResponse.success(PublishShareFolderResponseDto.from(shareId)));
    }

}

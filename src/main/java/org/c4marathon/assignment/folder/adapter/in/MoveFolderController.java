package org.c4marathon.assignment.folder.adapter.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.folder.application.port.in.MoveFolderUseCase;
import org.c4marathon.assignment.global.response.ApiResponse;
import org.c4marathon.assignment.user.application.port.in.GetUserProfileUseCase;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/folders/move")
public class MoveFolderController {

    private final GetUserProfileUseCase getUserProfileUseCase;

    private final MoveFolderUseCase moveFolderUseCase;

    @PutMapping
    public ResponseEntity<ApiResponse<Void>> moveFolder(@NotBlank @Param("email") String email,
                                                        @NotNull @Param("folderId") Long folderId,
                                                        @Param("destFolderId") Long destFolderId) {
        User user = getUserProfileUseCase.getUserByEmail(email);
        moveFolderUseCase.moveFolder(user, folderId, destFolderId);
        return ResponseEntity.ok(ApiResponse.success());
    }

}

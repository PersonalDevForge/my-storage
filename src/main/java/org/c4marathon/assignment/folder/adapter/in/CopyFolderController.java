package org.c4marathon.assignment.folder.adapter.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.folder.application.port.in.CopyFolderUseCase;
import org.c4marathon.assignment.global.response.ApiResponse;
import org.c4marathon.assignment.user.application.port.in.GetUserProfileUseCase;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/folders/copy")
public class CopyFolderController {

    private final CopyFolderUseCase copyFolderUseCase;

    private final GetUserProfileUseCase getUserProfileUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> copyFolder(@NotBlank @Param("email") String email,
                                                        @NotNull @Param("folderId") Long folderId) {
        User user = getUserProfileUseCase.getUserByEmail(email);
        copyFolderUseCase.copyFolder(user, folderId);
        return ResponseEntity.ok(ApiResponse.success());
    }

}

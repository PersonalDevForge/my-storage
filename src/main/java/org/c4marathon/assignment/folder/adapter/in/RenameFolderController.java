package org.c4marathon.assignment.folder.adapter.in;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.folder.application.port.in.RenameFolderUseCase;
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
@RequestMapping("/api/v1/folders/rename")
public class RenameFolderController {

    private final RenameFolderUseCase renameFolderUseCase;

    private final GetUserProfileUseCase getUserProfileUseCase;

    @PutMapping
    public ResponseEntity<ApiResponse<Void>> renameFolder(@NotBlank @Param("email") String email,
                                                          @NotBlank @Param("folderId") Long folderId,
                                                          @NotBlank @Param("newFolderName") String newFolderName) {
        User user = getUserProfileUseCase.getUserByEmail(email);
        renameFolderUseCase.renameFolder(user, folderId, newFolderName);
        return ResponseEntity.ok(ApiResponse.success());
    }

}

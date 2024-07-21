package org.c4marathon.assignment.folder.adapter.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.folder.application.port.in.MakeFolderUseCase;
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
@RequestMapping("/api/v1/folders/make")
public class MakeFolderController {

    private final MakeFolderUseCase makeFolderUseCase;

    private final GetUserProfileUseCase getUserProfileUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> makeFolder(@NotBlank @Param("email") String email,
                                                        @NotNull @NotBlank @Param("folderName") String folderName,
                                                        @Param("parentFolderId") Long parentFolderId) {
        User user = getUserProfileUseCase.getUserByEmail(email);
        makeFolderUseCase.makeFolder(user, folderName, parentFolderId);

        return ResponseEntity.ok(ApiResponse.success());
    }

}

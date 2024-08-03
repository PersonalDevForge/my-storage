package org.c4marathon.assignment.file.adapter.in;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.in.MoveFileUseCase;
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
@RequestMapping("/api/v1/files/move")
public class MoveFileController {

    private final MoveFileUseCase moveFileUseCase;

    private final GetUserProfileUseCase getUserProfileUseCase;

    @PutMapping
    public ResponseEntity<ApiResponse<Void>> moveFile(@NotEmpty @Param("email") String email,
                                                      @Positive @Param("fileId") Long fileId,
                                                      @Nullable @Param("folderId") Long folderId) {
        User user = getUserProfileUseCase.getUserByEmail(email);
        moveFileUseCase.moveFile(user, fileId, folderId);
        return ResponseEntity.ok(ApiResponse.success());
    }

}

package org.c4marathon.assignment.file.adapter.in;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.in.CopyFileUseCase;
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
@RequestMapping("/api/v1/files/copy")
public class CopyFileController {

    private final CopyFileUseCase copyFileUseCase;

    private final GetUserProfileUseCase getUserProfileUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> copyFile(@NotEmpty @Param("email") String email,
                                                      @Positive @Param("fileId") Long fileId) {
        User user = getUserProfileUseCase.getUserByEmail(email);
        copyFileUseCase.copyFile(user, fileId);
        return ResponseEntity.ok(ApiResponse.success());
    }

}

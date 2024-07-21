package org.c4marathon.assignment.file.adapter.in;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.in.DeleteFileUseCase;
import org.c4marathon.assignment.global.response.ApiResponse;
import org.c4marathon.assignment.user.application.port.in.GetUserProfileUseCase;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files/delete")
public class DeleteFileController {

    private final DeleteFileUseCase deleteFileUseCase;

    private final GetUserProfileUseCase getUserProfileUseCase;

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteFile(@NotBlank @Param("email") String email,
                                                        @NotBlank @Param("filename") String filename) {
        User user = getUserProfileUseCase.getUserByEmail(email);
        deleteFileUseCase.deleteFile(user, filename);

        return ResponseEntity.ok(ApiResponse.success());
    }

}

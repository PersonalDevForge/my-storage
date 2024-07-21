package org.c4marathon.assignment.file.adapter.in;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.in.GetFileListUseCase;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.global.response.ApiResponse;
import org.c4marathon.assignment.user.application.port.in.GetUserProfileUseCase;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
public class GetFileController {

    private final GetFileListUseCase getFileListUseCase;

    private final GetUserProfileUseCase getUserProfileUseCase;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<File>>> getFileList(@NotBlank @RequestParam("email") String email) {
        User user = getUserProfileUseCase.getUserByEmail(email);
        List<File> fileList = getFileListUseCase.getFileList(user);
        return ResponseEntity.ok(ApiResponse.success(fileList));
    }

}

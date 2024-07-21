package org.c4marathon.assignment.file.adapter.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.c4marathon.assignment.file.application.port.in.UploadFileUseCase;
import org.c4marathon.assignment.global.response.ApiResponse;
import org.c4marathon.assignment.global.response.enums.ResultCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files/upload")
public class UploadFileController {

    private final UploadFileUseCase uploadFileUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> uploadFile(@NotBlank @RequestParam("email") String email,
                                                  @NotEmpty @RequestParam("file") MultipartFile file) {
        try {
            uploadFileUseCase.uploadFile(email, file.getOriginalFilename(), file.getBytes());
            return ResponseEntity.ok(ApiResponse.success());
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.failure(ResultCode.BAD_REQUEST, "Failed to upload file: " + e.getMessage()));
        }
    }

}

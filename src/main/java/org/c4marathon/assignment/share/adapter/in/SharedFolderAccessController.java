package org.c4marathon.assignment.share.adapter.in;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.global.response.ApiResponse;
import org.c4marathon.assignment.global.response.enums.ResultCode;
import org.c4marathon.assignment.share.application.service.UploadFileToSharedFolderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/share/folders/access")
public class SharedFolderAccessController {

    private final UploadFileToSharedFolderService uploadFileToSharedFolderService;

    @PostMapping("/{uuid}/upload")
    public ResponseEntity<ApiResponse<Void>> uploadFile(@NotEmpty @RequestParam("file") MultipartFile file,
                                                        @Nullable @PathVariable("uuid") String uuid) {
        try {
            uploadFileToSharedFolderService.uploadFileToSharedFolder(uuid, file.getOriginalFilename(), file.getBytes());
            return ResponseEntity.ok(ApiResponse.success());
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.failure(ResultCode.BAD_REQUEST, "Failed to upload file: " + e.getMessage()));
        }
    }

}

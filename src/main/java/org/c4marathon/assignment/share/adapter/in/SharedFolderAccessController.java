package org.c4marathon.assignment.share.adapter.in;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.global.response.ApiResponse;
import org.c4marathon.assignment.global.response.enums.ResultCode;
import org.c4marathon.assignment.share.application.port.in.DeleteFileToSharedFolderUseCase;
import org.c4marathon.assignment.share.application.port.in.DownloadFileFromSharedFolderUseCase;
import org.c4marathon.assignment.share.application.service.UploadFileToSharedFolderService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/share/folders/access")
public class SharedFolderAccessController {

    private final UploadFileToSharedFolderService uploadFileToSharedFolderService;

    private final DeleteFileToSharedFolderUseCase deleteFileToSharedFolderUseCase;

    private final DownloadFileFromSharedFolderUseCase downloadFileFromSharedFolderUseCase;

    @GetMapping("/{uuid}/download")
    public ResponseEntity<?> downloadFile(@Positive @RequestParam("fileId") Long fileId,
                                          @Nullable @PathVariable("uuid") String uuid) {
        String pathString = downloadFileFromSharedFolderUseCase.downloadFileFromSharedFolder(uuid, fileId);
        Path path = Path.of(pathString).toAbsolutePath().normalize();

        try {
            Resource resource = new UrlResource(path.toUri());

            if (!resource.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            String contentType = "application/octet-stream";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=" + resource.getFilename());

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

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

    @DeleteMapping("/{uuid}/delete")
    public ResponseEntity<ApiResponse<Void>> deleteFile(@Positive @RequestParam("fileId") Long fileId,
                                                        @Nullable @PathVariable("uuid") String uuid) {
        deleteFileToSharedFolderUseCase.deleteFileToSharedFolder(uuid, fileId);
        return ResponseEntity.ok(ApiResponse.success());
    }

}

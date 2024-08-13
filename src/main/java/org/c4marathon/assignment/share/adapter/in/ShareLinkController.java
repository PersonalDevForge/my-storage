package org.c4marathon.assignment.share.adapter.in;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.global.response.ApiResponse;
import org.c4marathon.assignment.share.application.port.in.DownloadSharedFileUseCase;
import org.c4marathon.assignment.share.application.port.in.PublishDownloadFileLinkUseCase;
import org.c4marathon.assignment.user.application.port.in.GetUserProfileUseCase;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/share")
public class ShareLinkController {

    private final GetUserProfileUseCase getUserProfileUseCase;

    private final PublishDownloadFileLinkUseCase publishDownloadFileLinkUseCase;

    private final DownloadSharedFileUseCase downloadSharedFileUseCase;

    @PostMapping("/{id}/publish")
    public ResponseEntity<ApiResponse<String>> publishDownloadFileLink(@NotEmpty @Param("email") String email,
                                                                       @NotNull @PathVariable("id") Long fileId) {
        User user = getUserProfileUseCase.getUserByEmail(email);
        String downloadLink = publishDownloadFileLinkUseCase.publishDownloadFileLink(user, fileId);
        return ResponseEntity.ok(ApiResponse.success(downloadLink));
    }

    @GetMapping("/files/download/{uuid}")
    public ResponseEntity<?> downloadFile(@NotNull @PathVariable("uuid") String uuid) {
        String pathString = downloadSharedFileUseCase.downloadSharedFile(uuid);
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

}

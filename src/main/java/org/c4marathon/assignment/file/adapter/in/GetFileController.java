package org.c4marathon.assignment.file.adapter.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.adapter.in.dto.GetFileListRequestDto;
import org.c4marathon.assignment.file.application.port.in.GetFileListUseCase;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
public class GetFileController {

    private final GetFileListUseCase getFileListUseCase;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<File>>> getFileList(@NotBlank @RequestParam("email") String email) {
        List<File> fileList = getFileListUseCase.getFileList(email);
        return ResponseEntity.ok(ApiResponse.success(fileList));
    }

}

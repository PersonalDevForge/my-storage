package org.c4marathon.assignment.user.adapter.in;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.global.response.ApiResponse;
import org.c4marathon.assignment.global.response.enums.ResultCode;
import org.c4marathon.assignment.user.adapter.in.dto.request.JoinUserRequestDto;
import org.c4marathon.assignment.user.application.port.in.JoinUserUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class JoinUserController {

    private final JoinUserUseCase joinUserUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> joinUser(@Valid @RequestBody JoinUserRequestDto joinUserRequestDto) {
        try {
            joinUserUseCase.joinUser(joinUserRequestDto.getName(), joinUserRequestDto.getEmail(), joinUserRequestDto.getPassword());
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(400).body(ApiResponse.failure(ResultCode.BAD_REQUEST, e.getMessage()));
        }
        return ResponseEntity.ok(ApiResponse.success());
    }

}

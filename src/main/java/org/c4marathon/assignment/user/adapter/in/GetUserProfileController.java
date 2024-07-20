package org.c4marathon.assignment.user.adapter.in;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.global.response.ApiResponse;
import org.c4marathon.assignment.user.adapter.in.dto.reseponse.UserProfileResponseDto;
import org.c4marathon.assignment.user.application.port.in.GetUserProfileUseCase;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/search")
public class GetUserProfileController {

    private final GetUserProfileUseCase getUserProfileUseCase;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfileResponseDto>> getUserProfile(@NotNull @Positive Long userId) {
        User user = getUserProfileUseCase.getUserProfile(userId);
        UserProfileResponseDto responseDto = UserProfileResponseDto.builder().email(user.getEmail()).name(user.getNickname()).build();
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

}

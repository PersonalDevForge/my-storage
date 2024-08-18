package org.c4marathon.assignment.user.application.service;

import org.c4marathon.assignment.global.exception.customs.NotFoundException;
import org.c4marathon.assignment.user.application.port.out.UserStorageQueryPort;
import org.c4marathon.assignment.user.domain.entity.User;
import org.c4marathon.assignment.user.domain.entity.UserStorage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class GetUserStorageServiceTest {

    @Mock
    private UserStorageQueryPort userStorageQueryPort;

    @InjectMocks
    private GetUserStorageService getUserStorageService;

    private final User user = User.of(1L, "test@example.com", "username", "password");
    private final UserStorage userStorage = UserStorage.of(1L, user, 100L, 50L);

    @Test
    @DisplayName("유저 Id로 유저 스토리지를 조회할 수 있다.")
    void getUserStorage() {
        // given
        when(userStorageQueryPort.findByUserId(1L)).thenReturn(Optional.of(userStorage));

        // when
        UserStorage result = getUserStorageService.getUserStorage(1L);

        // then
        assertThat(result.getUser()).isNotNull();
        assertThat(result.getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("존재하지 않는 유저의 Id로 조회하면 NotFoundException이 발생한다.")
    void getUserStorageNotFound() {
        // given
        when(userStorageQueryPort.findByUserId(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> getUserStorageService.getUserStorage(1L));
    }

}
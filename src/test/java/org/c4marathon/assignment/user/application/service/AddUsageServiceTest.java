package org.c4marathon.assignment.user.application.service;

import org.c4marathon.assignment.user.application.port.in.GetUserStorageUseCase;
import org.c4marathon.assignment.user.domain.entity.User;
import org.c4marathon.assignment.user.domain.entity.UserStorage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AddUsageServiceTest {

    @Mock
    private GetUserStorageUseCase getUserStorageUseCase;

    @InjectMocks
    private AddUsageService addUsageService;

    private final User user = User.of(1L, "test@example.com", "username", "password");
    private final UserStorage userStorage = UserStorage.of(1L, user, 100L, 50L);

    @Test
    @DisplayName("유저 스토리지에 사용량을 추가할 수 있다.")
    void addUsageUseCase() {
        // given
        when(getUserStorageUseCase.getUserStorage(1L)).thenReturn(userStorage);

        // when
        addUsageService.addUsageUseCase(1L, 25L);

        // then
        assertThat(userStorage.getCurrentUsage()).isEqualTo(75L);
    }

}
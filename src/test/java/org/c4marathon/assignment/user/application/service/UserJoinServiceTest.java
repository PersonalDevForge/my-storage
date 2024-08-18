package org.c4marathon.assignment.user.application.service;

import org.c4marathon.assignment.user.application.port.out.UserCommandPort;
import org.c4marathon.assignment.user.application.port.out.UserQueryPort;
import org.c4marathon.assignment.user.application.port.out.UserStorageCommandPort;
import org.c4marathon.assignment.user.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserJoinServiceTest {

    @Mock
    private UserCommandPort userCommandPort;

    @Mock
    private UserQueryPort userQueryPort;

    @Mock
    private UserStorageCommandPort userStorageCommandPort;

    @InjectMocks
    private UserJoinService userJoinService;

    private final User user = User.of(1L, "test@example.com", "username", "password");

    @Test
    @DisplayName("동일한 이메일의 유저가 존재하지 않는다면, 회원가입 할 수 있다.")
    void joinUser() {
        // given
        String name = "test";
        String email = "test@example.com";
        String password = "password";
        when(userQueryPort.findByEmail(email)).thenReturn(Optional.empty());

        // when & then
        assertDoesNotThrow(() -> userJoinService.joinUser(name, email, password));
    }


    @Test
    @DisplayName("이미 동일한 이메일이 존재하면 회원가입에 실패한다.")
    void joinUserDuplicateEmail() {
        // given
        String name = "test";
        String email = "test@example.com";
        String password = "password";
        when(userQueryPort.findByEmail(email)).thenReturn(Optional.of(user));

        // when & then
        assertThrows(IllegalAccessException.class, () -> userJoinService.joinUser(name, email, password));
    }

}
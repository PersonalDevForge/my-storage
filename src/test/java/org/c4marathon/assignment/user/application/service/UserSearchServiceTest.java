package org.c4marathon.assignment.user.application.service;

import org.c4marathon.assignment.global.exception.customs.NotFoundException;
import org.c4marathon.assignment.user.application.port.out.UserQueryPort;
import org.c4marathon.assignment.user.domain.entity.User;
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
class UserSearchServiceTest {

    @Mock
    private UserQueryPort userQueryPort;

    @InjectMocks
    private UserSearchService userSearchService;

    private final User user = User.of(1L, "test@example.com", "username", "password");

    @Test
    @DisplayName("유저 Id로 유저를 조회할 수 있다.")
    void getUser() {
        // given
        when(userQueryPort.findById(1L)).thenReturn(Optional.of(user));

        // when
        User result = userSearchService.getUser(1L);

        // then
        assertThat(result.getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("존재하지 않는 유저 Id를 조회하면 NotFoundException이 발생한다.")
    void getUserNotFound() {
        // given
        when(userQueryPort.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> userSearchService.getUser(1L));
    }

    @Test
    @DisplayName("유저 Id로 유저 프로필을 조회할 수 있다.")
    void getUserProfile() {
        // given
        when(userQueryPort.findById(1L)).thenReturn(Optional.of(user));

        // when
        User result = userSearchService.getUserProfile(1L);

        // then
        assertThat(result.getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("존재하지 않는 유저 Id로 유저 프로필을 조회하면 NotFoundException이 발생한다.")
    void getUserProfileNotFound() {
        // given
        when(userQueryPort.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> userSearchService.getUserProfile(1L));
    }

    @Test
    @DisplayName("이메일로 유저를 조회할 수 있다.")
    void getUserByEmail() {
        // given
        when(userQueryPort.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        // when
        User result = userSearchService.getUserByEmail("test@example.com");

        // then
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("존재하지 않는 이메일로 유저를 조회하면 NotFoundException이 발생한다.")
    void getUserByEmailNotFound() {
        // given
        when(userQueryPort.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> userSearchService.getUserByEmail("test@example.com"));
    }

}
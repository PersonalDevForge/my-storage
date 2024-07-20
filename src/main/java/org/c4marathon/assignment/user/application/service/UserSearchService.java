package org.c4marathon.assignment.user.application.service;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.global.exception.customs.NotFoundException;
import org.c4marathon.assignment.user.application.port.in.GetUserProfileUseCase;
import org.c4marathon.assignment.user.application.port.out.UserQueryPort;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSearchService implements GetUserProfileUseCase {

    private final UserQueryPort userQueryPort;

    public User getUser(Long userId) {
        return userQueryPort.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public User getUserProfile(Long userId) {
        return getUser(userId);
    }

}

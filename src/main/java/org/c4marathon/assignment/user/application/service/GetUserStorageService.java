package org.c4marathon.assignment.user.application.service;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.global.exception.customs.NotFoundException;
import org.c4marathon.assignment.user.application.port.in.GetUserStorageUseCase;
import org.c4marathon.assignment.user.application.port.out.UserStorageQueryPort;
import org.c4marathon.assignment.user.domain.entity.UserStorage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserStorageService implements GetUserStorageUseCase {

    private final UserStorageQueryPort userStorageQueryPort;

    @Override
    public UserStorage getUserStorage(Long userId) {
        return userStorageQueryPort.findByUserId(userId).orElseThrow(() -> new NotFoundException("UserStorage not found"));
    }

}

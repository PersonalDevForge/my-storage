package org.c4marathon.assignment.user.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.user.application.port.in.AddUsageUseCase;
import org.c4marathon.assignment.user.application.port.in.GetUserStorageUseCase;
import org.c4marathon.assignment.user.domain.entity.UserStorage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddUsageService implements AddUsageUseCase {

    private final GetUserStorageUseCase getUserStorageUseCase;

    @Override
    @Transactional
    public void AddUsageUseCase(Long userId, Long amount) {
        UserStorage userStorage = getUserStorageUseCase.getUserStorage(userId);
        userStorage.addUsage(amount);
    }

}

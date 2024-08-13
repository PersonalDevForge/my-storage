package org.c4marathon.assignment.user.adapter.out;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.user.application.port.out.UserStorageQueryPort;
import org.c4marathon.assignment.user.domain.entity.UserStorage;
import org.c4marathon.assignment.user.infrastructure.repository.UserStorageRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserStorageQueryAdapter implements UserStorageQueryPort {

    private final UserStorageRepository userStorageRepository;

    @Override
    public Optional<UserStorage> findByUserId(Long userId) {
        return userStorageRepository.findByUserId(userId);
    }

}

package org.c4marathon.assignment.user.adapter.out;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.user.application.port.out.UserStorageCommandPort;
import org.c4marathon.assignment.user.domain.entity.UserStorage;
import org.c4marathon.assignment.user.infrastructure.repository.UserStorageRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserStorageCommandAdapter implements UserStorageCommandPort {

    private final UserStorageRepository userStorageRepository;

    @Override
    public UserStorage save(UserStorage userStorage) {
        return userStorageRepository.save(userStorage);
    }

    @Override
    public void delete(UserStorage userStorage) {
        userStorageRepository.delete(userStorage);
    }
}

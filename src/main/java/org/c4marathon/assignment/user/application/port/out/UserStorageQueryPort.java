package org.c4marathon.assignment.user.application.port.out;

import org.c4marathon.assignment.user.domain.entity.UserStorage;

import java.util.Optional;

public interface UserStorageQueryPort {
    Optional<UserStorage> findByUserId(Long userId);
}

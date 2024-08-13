package org.c4marathon.assignment.user.application.port.out;

import org.c4marathon.assignment.user.domain.entity.UserStorage;

public interface UserStorageCommandPort {

    UserStorage save(UserStorage userStorage);

    void delete(UserStorage userStorage);

}

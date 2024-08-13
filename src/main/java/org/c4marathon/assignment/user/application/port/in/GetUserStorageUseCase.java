package org.c4marathon.assignment.user.application.port.in;

import org.c4marathon.assignment.user.domain.entity.UserStorage;

public interface GetUserStorageUseCase {

    UserStorage getUserStorage(Long userId);

}

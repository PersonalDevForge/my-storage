package org.c4marathon.assignment.user.application.port.in;

import org.c4marathon.assignment.user.domain.entity.User;

public interface GetUserProfileUseCase {

    User getUserProfile(Long userId);

}

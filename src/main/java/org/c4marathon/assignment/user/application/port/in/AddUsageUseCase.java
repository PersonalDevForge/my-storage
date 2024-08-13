package org.c4marathon.assignment.user.application.port.in;

import org.c4marathon.assignment.user.domain.entity.User;

public interface AddUsageUseCase {

    void AddUsageUseCase(Long userId, Long amount);

}

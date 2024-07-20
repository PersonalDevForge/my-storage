package org.c4marathon.assignment.user.application.port.out;

import org.c4marathon.assignment.user.domain.entity.User;

public interface UserCommandPort {

    User save(User user);

    User update(User user);

    void delete(Long userId);

}

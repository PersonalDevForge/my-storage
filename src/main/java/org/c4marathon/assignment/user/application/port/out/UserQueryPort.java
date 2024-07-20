package org.c4marathon.assignment.user.application.port.out;

import org.c4marathon.assignment.user.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserQueryPort {

    Optional<User> findById(Long userId);

    Optional<User> findByEmail(String email);

    List<User> findAll();

}

package org.c4marathon.assignment.user.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.user.application.port.in.JoinUserUseCase;
import org.c4marathon.assignment.user.application.port.out.UserCommandPort;
import org.c4marathon.assignment.user.application.port.out.UserQueryPort;
import org.c4marathon.assignment.user.application.port.out.UserStorageCommandPort;
import org.c4marathon.assignment.user.domain.entity.User;
import org.c4marathon.assignment.user.domain.entity.UserStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserJoinService implements JoinUserUseCase {

    private final UserCommandPort userCommandPort;

    private final UserQueryPort userQueryPort;

    private final UserStorageCommandPort userStorageCommandPort;

    @Value("${user.storage.default.capacity}")
    private Long defaultCapacity;

    @Override
    @Transactional
    public void joinUser(String name, String email, String password) throws IllegalAccessException {
        Optional<User> mustNotExistUser = userQueryPort.findByEmail(email);

        if (mustNotExistUser.isPresent()) {
            throw new IllegalAccessException("email already exists");
        }

        User user = User.of(email, name, password);
        userCommandPort.save(user);
        userStorageCommandPort.save(UserStorage.of(null, user, defaultCapacity, 0L));
    }

}

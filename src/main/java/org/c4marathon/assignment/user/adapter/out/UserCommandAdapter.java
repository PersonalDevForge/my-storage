package org.c4marathon.assignment.user.adapter.out;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.user.application.port.out.UserCommandPort;
import org.c4marathon.assignment.user.domain.entity.User;
import org.c4marathon.assignment.user.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserCommandAdapter implements UserCommandPort {

    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }

}

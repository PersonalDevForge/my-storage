package org.c4marathon.assignment.user.infrastructure.repository;

import org.c4marathon.assignment.user.domain.entity.UserStorage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserStorageRepository extends JpaRepository<UserStorage, Long> {

    Optional<UserStorage> findByUserId(Long userId);

}

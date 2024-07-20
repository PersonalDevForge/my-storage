package org.c4marathon.assignment.user.infrastructure.repository;

import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

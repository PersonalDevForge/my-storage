package org.c4marathon.assignment.file.infrastructure.repository;

import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findAllByUser(User user);

}

package org.c4marathon.assignment.file.infrastructure.repository;

import org.c4marathon.assignment.file.domain.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}

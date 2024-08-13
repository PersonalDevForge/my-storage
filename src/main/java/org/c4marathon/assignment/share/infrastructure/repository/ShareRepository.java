package org.c4marathon.assignment.share.infrastructure.repository;

import org.c4marathon.assignment.share.domain.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShareRepository extends JpaRepository<Share, String> {
}

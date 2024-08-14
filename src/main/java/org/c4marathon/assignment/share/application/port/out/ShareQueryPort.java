package org.c4marathon.assignment.share.application.port.out;

import org.c4marathon.assignment.share.domain.entity.Share;

import java.util.Optional;

public interface ShareQueryPort {

    Optional<Share> findById(String id);

}

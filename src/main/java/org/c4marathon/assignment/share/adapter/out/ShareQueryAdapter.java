package org.c4marathon.assignment.share.adapter.out;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.share.application.port.out.ShareQueryPort;
import org.c4marathon.assignment.share.domain.entity.Share;
import org.c4marathon.assignment.share.infrastructure.repository.ShareRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ShareQueryAdapter implements ShareQueryPort {

    private final ShareRepository shareRepository;

    @Override
    public Optional<Share> findById(String id) {
        return shareRepository.findById(id);
    }

}

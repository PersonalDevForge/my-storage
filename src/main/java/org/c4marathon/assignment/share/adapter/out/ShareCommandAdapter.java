package org.c4marathon.assignment.share.adapter.out;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.share.application.port.out.ShareCommandPort;
import org.c4marathon.assignment.share.domain.entity.Share;
import org.c4marathon.assignment.share.infrastructure.repository.ShareRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ShareCommandAdapter implements ShareCommandPort {

    private final ShareRepository shareRepository;

    @Override
    public Share save(Share share) {
        return shareRepository.save(share);
    }

    @Override
    public void delete(Share share) {
        shareRepository.delete(share);
    }

}

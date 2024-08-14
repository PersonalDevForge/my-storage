package org.c4marathon.assignment.share.application.port.out;

import org.c4marathon.assignment.share.domain.entity.Share;

public interface ShareCommandPort {

    Share save(Share share);

    void delete(Share share);

}

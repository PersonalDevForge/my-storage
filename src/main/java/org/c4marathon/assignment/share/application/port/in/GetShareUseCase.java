package org.c4marathon.assignment.share.application.port.in;

import org.c4marathon.assignment.share.domain.entity.Share;

public interface GetShareUseCase {

    Share getShare(String uuid);

}

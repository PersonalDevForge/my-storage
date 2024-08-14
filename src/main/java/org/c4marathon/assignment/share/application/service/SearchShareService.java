package org.c4marathon.assignment.share.application.service;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.global.exception.customs.NotFoundException;
import org.c4marathon.assignment.share.application.port.in.GetShareUseCase;
import org.c4marathon.assignment.share.application.port.out.ShareQueryPort;
import org.c4marathon.assignment.share.domain.entity.Share;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchShareService implements GetShareUseCase {

    private final ShareQueryPort shareQueryPort;

    @Override
    public Share getShare(String uuid) {
        return shareQueryPort.findById(uuid).orElseThrow(() -> new NotFoundException("Share not found"));
    }
}

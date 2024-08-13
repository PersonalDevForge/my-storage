package org.c4marathon.assignment.share.application.service;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.service.FileSearchService;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.global.exception.customs.ExpiredLinkException;
import org.c4marathon.assignment.share.application.port.in.DownloadSharedFileUseCase;
import org.c4marathon.assignment.share.application.port.out.ShareCommandPort;
import org.c4marathon.assignment.share.domain.entity.Share;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DownloadSharedFileService implements DownloadSharedFileUseCase {

    private final ShareCommandPort shareCommandPort;

    private final FileSearchService fileSearchService;

    private final SearchShareService searchShareService;

    @Override
    public String downloadSharedFile(String uuid) {
        Share share = searchShareService.getShare(uuid);
        if (share.isExpired()) {
            shareCommandPort.delete(share);
            throw new ExpiredLinkException("만료된 다운로드 링크입니다.");
        }

        File file = fileSearchService.getFile(share.getUser(), share.getFile().getId());
        return file.getPath();
    }
}

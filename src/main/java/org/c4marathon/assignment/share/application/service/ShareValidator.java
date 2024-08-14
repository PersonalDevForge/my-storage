package org.c4marathon.assignment.share.application.service;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.service.FileSearchService;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.share.application.port.out.ShareCommandPort;
import org.c4marathon.assignment.share.domain.entity.Share;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShareValidator {

    private final ShareCommandPort shareCommandPort;

    private final FileSearchService fileSearchService;

    public void validateFolderShare(Share share) {
        if (!share.getIsFolder()) {
            throw new IllegalArgumentException("유효하지 않은 uuid 입니다.");
        }
        if (share.isExpired()) {
            shareCommandPort.delete(share);
            throw new IllegalArgumentException("폴더 공유가 만료되었습니다.");
        }
    }

    public void validateIsInSharedFolder(Share share, Long fileId) {
        File file = fileSearchService.getFile(share.getUser(), fileId);
        if (share.getFolder() == null) {
            if (file.getFolder() != null) {
                throw new IllegalArgumentException("폴더 공유에 속하지 않은 파일입니다.");
            }
        }
        if (file.getFolder() == null) {
            throw new IllegalArgumentException("폴더 공유에 속하지 않은 파일입니다.");
        }
        if (!share.getFolder().getId().equals(file.getFolder().getId())) {
            throw new IllegalArgumentException("폴더 공유에 속하지 않은 파일입니다.");
        }
    }

}

package org.c4marathon.assignment.share.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.service.FileSearchService;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.share.application.port.in.PublishDownloadFileLinkUseCase;
import org.c4marathon.assignment.share.application.port.out.ShareCommandPort;
import org.c4marathon.assignment.share.domain.entity.Share;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PublishDownloadFileLinkService implements PublishDownloadFileLinkUseCase {

    private final ShareCommandPort shareCommandPort;

    private final FileSearchService fileSearchService;

    private final Long EXPIRATION_HOURS = 3L;

    @Value("${app.base-url}")
    private String baseUrl;

    private String generateDownloadLink(String uuid) {
        return baseUrl + "/api/v1/share/files/download/" + uuid;
    }

    @Override
    @Transactional
    public String publishDownloadFileLink(User user, Long fileId) {
        String uuid = UUID.randomUUID().toString();
        File file = fileSearchService.getFile(user, fileId);
        LocalDateTime expiration = LocalDateTime.now().plusHours(EXPIRATION_HOURS);
        Share share = Share.of(uuid, user, file, null, false, expiration);
        shareCommandPort.save(share);
        return generateDownloadLink(uuid);
    }

}

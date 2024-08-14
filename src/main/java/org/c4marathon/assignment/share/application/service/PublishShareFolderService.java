package org.c4marathon.assignment.share.application.service;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.folder.application.service.FolderSearchService;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.share.application.port.in.PublishShareFolderUseCase;
import org.c4marathon.assignment.share.application.port.out.ShareCommandPort;
import org.c4marathon.assignment.share.domain.entity.Share;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PublishShareFolderService implements PublishShareFolderUseCase {

    private final ShareCommandPort shareCommandPort;

    private final FolderSearchService folderSearchService;

    private final Long EXPIRATION_HOURS = 3L;

    @Override
    public String publishShareFolder(User user, Long folderId) {
        String uuid = UUID.randomUUID().toString();
        Folder folder = folderSearchService.findById(user, folderId);
        LocalDateTime expiration = LocalDateTime.now().plusHours(EXPIRATION_HOURS);
        Share share = Share.of(uuid, user, null, folder, true, expiration);
        shareCommandPort.save(share);
        return uuid;
    }

}

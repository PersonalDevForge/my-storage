package org.c4marathon.assignment.share.application.service;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.in.DownloadFileUseCase;
import org.c4marathon.assignment.share.application.port.in.DownloadFileFromSharedFolderUseCase;
import org.c4marathon.assignment.share.application.port.in.GetShareUseCase;
import org.c4marathon.assignment.share.domain.entity.Share;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DownloadFileFromSharedFolderService implements DownloadFileFromSharedFolderUseCase {

    private final GetShareUseCase getShareUseCase;

    private final ShareValidator shareValidator;

    private final DownloadFileUseCase downloadFileUseCase;

    @Override
    public String downloadFileFromSharedFolder(String uuid, Long fileId) {
        Share share = getShareUseCase.getShare(uuid);
        shareValidator.validateFolderShare(share);
        shareValidator.validateIsInSharedFolder(share, fileId);
        return downloadFileUseCase.downloadFile(share.getUser(), fileId);
    }

}

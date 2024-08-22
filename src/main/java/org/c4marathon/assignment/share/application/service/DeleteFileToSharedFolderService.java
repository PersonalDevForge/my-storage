package org.c4marathon.assignment.share.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.in.DeleteFileUseCase;
import org.c4marathon.assignment.share.application.port.in.DeleteFileToSharedFolderUseCase;
import org.c4marathon.assignment.share.application.port.in.GetShareUseCase;
import org.c4marathon.assignment.share.domain.entity.Share;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteFileToSharedFolderService implements DeleteFileToSharedFolderUseCase {

    private final GetShareUseCase getShareUseCase;

    private final ShareValidator shareValidator;

    private final DeleteFileUseCase deleteFileUseCase;

    @Override
    @Transactional
    public void deleteFileToSharedFolder(String uuid, Long fileId) {
        Share share = getShareUseCase.getShare(uuid);
        shareValidator.validateFolderShare(share);
        shareValidator.validateIsInSharedFolder(share, fileId);
        deleteFileUseCase.deleteFile(share.getUser(), fileId);
    }

}

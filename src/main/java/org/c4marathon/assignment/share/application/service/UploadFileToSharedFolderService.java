package org.c4marathon.assignment.share.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.in.UploadFileUseCase;
import org.c4marathon.assignment.share.application.port.in.GetShareUseCase;
import org.c4marathon.assignment.share.application.port.in.UploadFileToSharedFolderUseCase;
import org.c4marathon.assignment.share.domain.entity.Share;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UploadFileToSharedFolderService implements UploadFileToSharedFolderUseCase {

    private final GetShareUseCase getShareUseCase;

    private final ShareValidator shareValidator;

    private final UploadFileUseCase uploadFileUseCase;

    @Override
    @Transactional
    public void uploadFileToSharedFolder(String uuid, String fileName, byte[] file) {
        Share share = getShareUseCase.getShare(uuid);
        shareValidator.validateFolderShare(share);
        Long folderId = share.getFolder() == null ? null : share.getFolder().getId();
        uploadFileUseCase.uploadFile(share.getUser(), fileName, folderId, file);
    }

}

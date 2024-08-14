package org.c4marathon.assignment.share.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.in.UploadFileUseCase;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.share.application.port.in.GetShareUseCase;
import org.c4marathon.assignment.share.application.port.in.UploadFileToSharedFolderUseCase;
import org.c4marathon.assignment.share.application.port.out.ShareCommandPort;
import org.c4marathon.assignment.share.domain.entity.Share;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UploadFileToSharedFolderService implements UploadFileToSharedFolderUseCase {

    private final GetShareUseCase getShareUseCase;

    private final ShareCommandPort shareCommandPort;

    private final UploadFileUseCase uploadFileUseCase;

    @Override
    @Transactional
    public void uploadFileToSharedFolder(String uuid, String fileName, byte[] file) {
        Share share = getShareUseCase.getShare(uuid);
        validateFolderShare(share);
        uploadFileUseCase.uploadFile(share.getUser(), fileName, share.getFolder().getId(), file);
    }

    private void validateFolderShare(Share share) {
        if (!share.getIsFolder()) {
            throw new IllegalArgumentException("유효하지 않은 uuid 입니다.");
        }
        if (share.isExpired()) {
            shareCommandPort.delete(share);
            throw new IllegalArgumentException("폴더 공유가 만료되었습니다.");
        }
    }

}

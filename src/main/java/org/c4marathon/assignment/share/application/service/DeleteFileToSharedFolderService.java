package org.c4marathon.assignment.share.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.in.DeleteFileUseCase;
import org.c4marathon.assignment.share.application.port.in.DeleteFileToSharedFolderUseCase;
import org.c4marathon.assignment.share.application.port.in.GetShareUseCase;
import org.c4marathon.assignment.share.application.port.out.ShareCommandPort;
import org.c4marathon.assignment.share.domain.entity.Share;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteFileToSharedFolderService implements DeleteFileToSharedFolderUseCase {

    private final GetShareUseCase getShareUseCase;

    private final ShareCommandPort shareCommandPort;

    private final DeleteFileUseCase deleteFileUseCase;

    @Override
    @Transactional
    public void deleteFileToSharedFolder(String uuid, Long fileId) {
        Share share = getShareUseCase.getShare(uuid);
        validateFolderShare(share);
        deleteFileUseCase.deleteFile(share.getUser(), fileId);
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

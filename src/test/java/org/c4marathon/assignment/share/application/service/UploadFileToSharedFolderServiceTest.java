package org.c4marathon.assignment.share.application.service;

import org.c4marathon.assignment.file.application.port.in.UploadFileUseCase;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.share.application.port.in.GetShareUseCase;
import org.c4marathon.assignment.share.domain.entity.Share;
import org.c4marathon.assignment.user.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.c4marathon.assignment.tools.TestTool.createFile;
import static org.c4marathon.assignment.tools.TestTool.createUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UploadFileToSharedFolderServiceTest {

    @Mock
    private GetShareUseCase getShareUseCase;

    @Mock
    private ShareValidator shareValidator;

    @Mock
    private UploadFileUseCase uploadFileUseCase;

    @InjectMocks
    private UploadFileToSharedFolderService uploadFileToSharedFolderService;

    @Test
    @DisplayName("공유받은 폴더의 UUID를 이용하여 누구나 파일을 업로드할 수 있다.")
    void uploadFileToSharedFolder() {
        // given
        User user = createUser();
        File file = createFile(user);
        String uuid = UUID.randomUUID().toString();
        LocalDateTime expiredAt = LocalDateTime.now().plusDays(1);
        Share share = Share.of(uuid, user, file, null, null, expiredAt);
        byte[] bytes = "Hello, World!".getBytes();

        // mock
        when(getShareUseCase.getShare(uuid)).thenReturn(share);

        // when & then
        assertDoesNotThrow(() -> uploadFileToSharedFolderService.uploadFileToSharedFolder(uuid, "file.txt", bytes));
    }

}
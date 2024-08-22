package org.c4marathon.assignment.share.application.service;

import org.c4marathon.assignment.file.application.port.in.DownloadFileUseCase;
import org.c4marathon.assignment.share.application.port.in.GetShareUseCase;
import org.c4marathon.assignment.share.domain.entity.Share;
import org.c4marathon.assignment.user.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class DownloadFileFromSharedFolderServiceTest {

    @Mock
    private GetShareUseCase getShareUseCase;

    @Mock
    private ShareValidator shareValidator;

    @Mock
    private DownloadFileUseCase downloadFileUseCase;

    @InjectMocks
    private DownloadFileFromSharedFolderService downloadFileFromSharedFolderService;

    @Test
    @DisplayName("공유받은 폴더 내부에 있는 파일을 누구나 다운로드 할 수 있다.")
    void downloadFileFromSharedFolder() {
        // given
        String uuid = "uuid";
        String fileDownloadPath = "file/download/path";
        User user = mock(User.class);
        Share share = mock(Share.class);
        when(getShareUseCase.getShare(uuid)).thenReturn(share);
        when(share.getUser()).thenReturn(user);
        when(downloadFileUseCase.downloadFile(share.getUser(), 1L)).thenReturn(fileDownloadPath);

        // when
        String result = downloadFileFromSharedFolderService.downloadFileFromSharedFolder(uuid, 1L);

        // then
        assertEquals(fileDownloadPath, result);
    }
}
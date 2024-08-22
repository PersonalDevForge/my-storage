package org.c4marathon.assignment.share.application.service;

import org.c4marathon.assignment.file.application.service.FileSearchService;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.global.exception.customs.ExpiredLinkException;
import org.c4marathon.assignment.share.application.port.out.ShareCommandPort;
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
class DownloadSharedFileServiceTest {

    @Mock
    private ShareCommandPort shareCommandPort;

    @Mock
    private FileSearchService fileSearchService;

    @Mock
    private SearchShareService searchShareService;

    @InjectMocks
    private DownloadSharedFileService downloadSharedFileService;

    @Test
    @DisplayName("만료되지 않은 다운로드 링크의 uuid을 이용하여 공유된 파일을 다운로드 받을 수 있다.")
    void downloadSharedFile() {
        // given
        String uuid = "uuid";
        User user = mock(User.class);
        Share share = mock(Share.class);
        File file = mock(File.class);
        when(searchShareService.getShare(uuid)).thenReturn(share);
        when(share.isExpired()).thenReturn(false);
        when(share.getFile()).thenReturn(file);
        when(file.getId()).thenReturn(1L);
        when(share.getUser()).thenReturn(user);
        when(fileSearchService.getFile(share.getUser(), share.getFile().getId())).thenReturn(file);
        when(file.getPath()).thenReturn("path");

        // when & then
        assertDoesNotThrow(() -> downloadSharedFileService.downloadSharedFile(uuid));
    }

    @Test
    @DisplayName("만료된 다운로드 링크의 uuid을 이용하여 공유된 파일을 다운로드 받으려하면 ExpiredLinkException을 던진다.")
    void downloadSharedFileExpiredLink() {
        // given
        String uuid = "uuid";
        Share share = mock(Share.class);
        when(searchShareService.getShare(uuid)).thenReturn(share);
        when(share.isExpired()).thenReturn(true);

        // when & then
        assertThrows(ExpiredLinkException.class, () -> downloadSharedFileService.downloadSharedFile(uuid));
    }

}
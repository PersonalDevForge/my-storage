package org.c4marathon.assignment.share.application.service;

import org.c4marathon.assignment.file.application.service.FileSearchService;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.share.application.port.out.ShareCommandPort;
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
class PublishDownloadFileLinkServiceTest {

    @Mock
    private ShareCommandPort shareCommandPort;

    @Mock
    private FileSearchService fileSearchService;

    @InjectMocks
    private PublishDownloadFileLinkService publishDownloadFileLinkService;

    @Test
    @DisplayName("파일 ID를 이용하여 다운로드 링크를 생성할 수 있다.")
    void publishDownloadFileLink() {
        // given
        User user = mock(User.class);
        File file = mock(File.class);
        String expectedSubLink = "/api/v1/share/files/download/";
        when(fileSearchService.getFile(user, 1L)).thenReturn(file);

        // when
        String result = publishDownloadFileLinkService.publishDownloadFileLink(user, 1L);

        // then
        assertTrue(result.contains(expectedSubLink));
    }

}
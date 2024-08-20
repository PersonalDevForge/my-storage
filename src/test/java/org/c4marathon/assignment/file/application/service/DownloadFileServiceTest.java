package org.c4marathon.assignment.file.application.service;

import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.user.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class DownloadFileServiceTest {

    @Mock
    private FileSearchService fileSearchService;

    @InjectMocks
    private DownloadFileService downloadFileService;

    @Test
    @DisplayName("유저는 파일의 id를 통해 파일을 다운로드 할 수 있다.")
    void userCanDownloadFile() {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        Long fileId = 1L;
        File file = File.of(1L, user, null, "src/test/resources/uuid.txt", "uuid","test.txt", "txt", 100L);
        given(fileSearchService.getFile(user, fileId)).willReturn(file);

        // when
        String path = downloadFileService.downloadFile(user, fileId);

        // then
        assertEquals("src/test/resources/uuid.txt", path);
    }

}
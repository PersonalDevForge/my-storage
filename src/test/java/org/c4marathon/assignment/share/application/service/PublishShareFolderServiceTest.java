package org.c4marathon.assignment.share.application.service;

import org.c4marathon.assignment.folder.application.service.FolderSearchService;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.share.application.port.out.ShareCommandPort;
import org.c4marathon.assignment.user.domain.entity.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class PublishShareFolderServiceTest {

    @Mock
    private ShareCommandPort shareCommandPort;

    @Mock
    private FolderSearchService folderSearchService;

    @InjectMocks
    private PublishShareFolderService publishShareFolderService;

    private static MockedStatic<LocalDateTime> localDateTimeMockedStatic;

    private static LocalDateTime now = LocalDateTime.of(2021, 1, 1, 0, 0);

    @BeforeAll
    public static void beforeAll() {
        localDateTimeMockedStatic = mockStatic(LocalDateTime.class);
        localDateTimeMockedStatic.when(LocalDateTime::now).thenReturn(now);
    }

    @AfterAll
    public static void afterAll() {
        localDateTimeMockedStatic.close();
    }

    @Test
    @DisplayName("folderId를 이용하여 Share를 생성할 수 있다.")
    void publishShareFolder() {
        // given
        User user = mock(User.class);
        Folder folder = mock(Folder.class);
        when(folderSearchService.findById(user, 1L)).thenReturn(folder);

        // when & then
        assertDoesNotThrow(() -> publishShareFolderService.publishShareFolder(user, 1L));
    }

}
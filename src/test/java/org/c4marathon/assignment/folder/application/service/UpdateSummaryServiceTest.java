package org.c4marathon.assignment.folder.application.service;

import org.c4marathon.assignment.file.application.service.FileSearchService;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.application.port.out.FolderCommandPort;
import org.c4marathon.assignment.folder.application.port.out.FolderQueryPort;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.user.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UpdateSummaryServiceTest {

    @Mock
    private FolderSearchService folderSearchService;

    @Mock
    private FolderQueryPort folderQueryPort;

    @Mock
    private FolderCommandPort folderCommandPort;

    @Mock
    private FileSearchService fileSearchService;

    @InjectMocks
    private UpdateSummaryService updateSummaryService;

    @Test
    @DisplayName("폴더 id를 이용하여 재귀적으로 폴더의 요약 정보를 업데이트 할 수 있다.")
    void updateSummaryUsingFolderId() {
        // given
        User user = mock(User.class);
        Folder folder1 = Folder.of(1L, user, null, "folder1", "src/test/resources", 1L, 0L, 1234L);
        Folder folder2 = Folder.of(2L, user, folder1,"folder2", "src/test/resources/folder1", 0L, 1L, 1234L);
        File file = File.of(1L, user, folder2, "src/test/resources/folder1/folder2/uuid.txt", "uuid", "fileName", "txt", 22L);
        LocalDateTime updateAt = LocalDateTime.of(2021, 1, 1, 0, 0);

        when(folderQueryPort.findByUserAndId(user, 1L)).thenReturn(Optional.of(folder1));
        when(folderQueryPort.findByUserAndId(user, 2L)).thenReturn(Optional.of(folder2));
        when(folderSearchService.findAllSubElementsById(user, 1L)).thenReturn(List.of(folder2));
        when(folderSearchService.findAllSubElementsById(user, 2L)).thenReturn(List.of());
        when(fileSearchService.getFileListByFolder(user, 2L)).thenReturn(List.of(file));

        // when
        updateSummaryService.updateSummary(user, 2L, updateAt);

        // then
        assertEquals(22L, folder2.getFolderSize());
        assertEquals(22L, folder1.getFolderSize());
        assertEquals(folder2.getUpdatedAt(), updateAt);
        assertEquals(folder1.getUpdatedAt(), updateAt);
    }

    @Test
    @DisplayName("폴더 id에 해당하는 폴더가 존재하지 않는다면 요약 정보를 업데이트하지 않는다.")
    void doNotUpdateSummaryIfFolderDoesNotExist() {
        // given
        User user = mock(User.class);
        when(folderQueryPort.findByUserAndId(user, 1L)).thenReturn(Optional.empty());

        // when
        updateSummaryService.updateSummary(user, 1L, LocalDateTime.now());

        // then
        verify(folderSearchService, times(0)).findAllSubElementsById(any(), any());
    }

}
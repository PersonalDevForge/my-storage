package org.c4marathon.assignment.folder.application.service;

import org.c4marathon.assignment.file.application.service.FileSearchService;
import org.c4marathon.assignment.file.domain.entity.File;
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
class UpdatePathServiceTest {

    @Mock
    private FolderSearchService folderSearchService;

    @Mock
    private FileSearchService fileSearchService;

    @InjectMocks
    private UpdatePathService updatePathService;

    @Test
    @DisplayName("부모 폴더의 변경된 이름/경로를 모든 자식 폴더와 파일에 재귀적으로 반영할 수 있다.")
    void updatePathInternal() {
        // given
        User user = mock(User.class);
        Folder folder1 = Folder.of(1L, user, null, "renamed", "src/test/resources/renamed", 1L, 0L, 1234L);
        Folder folder2 = Folder.of(2L, user, folder1,"folder2", "src/test/resources/folder1", 0L, 1L, 1234L);
        File file = File.of(1L, user, folder2, "src/test/resources/folder1/folder2/uuid.txt", "uuid", "fileName", "txt", 22L);

        when(folderSearchService.findAllSubElementsById(user, 1L)).thenReturn(List.of(folder2));
        when(fileSearchService.getFileListByFolder(user, 1L)).thenReturn(List.of());
        when(folderSearchService.findAllSubElementsById(user, 2L)).thenReturn(List.of());
        when(fileSearchService.getFileListByFolder(user, 2L)).thenReturn(List.of(file));

        // when
        updatePathService.updatePathInternal(folder1);

        // then
        verify(folderSearchService, times(1)).findAllSubElementsById(user, 1L);
        verify(folderSearchService, times(1)).findAllSubElementsById(user, 2L);
        verify(fileSearchService, times(1)).getFileListByFolder(user, 1L);
        verify(fileSearchService, times(1)).getFileListByFolder(user, 2L);
        assertEquals("src/test/resources/renamed/folder2", folder2.getPath());
        assertEquals("src/test/resources/renamed/folder2/uuid.txt", file.getPath());
    }

}
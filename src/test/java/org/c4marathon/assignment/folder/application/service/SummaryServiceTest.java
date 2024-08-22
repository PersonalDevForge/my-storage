package org.c4marathon.assignment.folder.application.service;

import org.c4marathon.assignment.file.application.service.FileSearchService;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.adapter.in.dto.FolderSummaryResponseDto;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.user.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class SummaryServiceTest {

    @Mock
    private FolderSearchService folderSearchService;

    @Mock
    private FileSearchService fileSearchService;

    @InjectMocks
    private SummaryService summaryService;

    @Test
    @DisplayName("루트 폴더의 요약 정보를 조회할 수 있다.")
    void getFolderSummaryOfRootFolder() {
        // given
        User user = mock(User.class);
        Folder targetFolder = null;
        Folder innerFolder = Folder.of(1L, user, targetFolder, "folderName", "path", 2L, 1L, 200L);
        File innerFile = File.of(1L, user, targetFolder, "path", "uuid", "fileName", "txt", 100L);

        // mock
        when(folderSearchService.findAllSubElementsById(user, null)).thenReturn(List.of(innerFolder));
        when(fileSearchService.getFileListByFolder(user, null)).thenReturn(List.of(innerFile));

        // when
        FolderSummaryResponseDto folderSummary = summaryService.getFolderSummary(user, null);

        // then
        assertEquals(1, folderSummary.getTotalFolders());
        assertEquals(1, folderSummary.getTotalFiles());
        assertEquals(300L, folderSummary.getTotalSize());
    }

    @Test
    @DisplayName("루트 폴더가 아닌 폴더의 요약 정보를 조회할 수 있다.")
    void getFolderSummaryOfNotARootFolder() {
        // given
        User user = mock(User.class);
        Folder targetFolder = Folder.of(1L, user, null, "folderName", "path", 1L, 1L, 300L);

        // mock
        when(folderSearchService.findById(user, 1L)).thenReturn(targetFolder);

        // when
        FolderSummaryResponseDto folderSummary = summaryService.getFolderSummary(user, targetFolder.getId());

        // then
        assertEquals(1, folderSummary.getTotalFolders());
        assertEquals(1, folderSummary.getTotalFiles());
        assertEquals(300L, folderSummary.getTotalSize());
    }

}
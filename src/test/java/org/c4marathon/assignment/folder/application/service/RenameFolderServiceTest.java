package org.c4marathon.assignment.folder.application.service;

import org.c4marathon.assignment.file.application.service.component.FileFactory;
import org.c4marathon.assignment.folder.application.port.in.UpdateSummaryUseCase;
import org.c4marathon.assignment.folder.application.port.out.FolderQueryPort;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.user.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class RenameFolderServiceTest {

    @Mock
    private FolderSearchService folderSearchService;

    @Mock
    private FolderQueryPort folderQueryPort;

    @Mock
    private UpdatePathService updatePathService;

    @Mock
    private UpdateSummaryUseCase updateSummaryUseCase;

    @Mock
    private FileFactory fileFactory;

    @InjectMocks
    private RenameFolderService renameFolderService;

    @Test
    @DisplayName("유저는 본인이 소유한 폴더의 이름을 변경할 수 있다.")
    void userCanRenameOwnFolder() {
        // given
        User user = mock(User.class);
        Folder folder = mock(Folder.class);
        Long folderId = 1L;
        String newFolderName = "newFolderName";
        when(folderQueryPort.findByUserAndName(user, newFolderName)).thenReturn(Optional.empty());
        when(folderSearchService.findById(user, folderId)).thenReturn(folder);
        File file = mock(File.class);
        when(folder.getPath()).thenReturn("path");
        when(fileFactory.createFile(folder.getPath())).thenReturn(file);
        File existingFolder = mock(File.class);
        when(file.getParent()).thenReturn("parent");
        when(fileFactory.createFile("parent" + File.separator + newFolderName)).thenReturn(existingFolder);
        when(existingFolder.exists()).thenReturn(false);
        when(file.renameTo(existingFolder)).thenReturn(true);

        // when & then
        assertDoesNotThrow(() -> renameFolderService.renameFolder(user, folderId, newFolderName));
    }

    @Test
    @DisplayName("이미 유저가 소유한 폴더 중 같은 이름의 폴더가 존재한다면 IllegalArgumentException을 던진다.")
    void userCanNotRenameIfExistSameFolderName() {
        // given
        User user = mock(User.class);
        Folder folder = mock(Folder.class);
        Long folderId = 1L;
        String newFolderName = "newFolderName";
        when(folderQueryPort.findByUserAndName(user, newFolderName)).thenReturn(Optional.of(folder));

        // when & then
        assertThrows(IllegalArgumentException.class, () -> renameFolderService.renameFolder(user, folderId, newFolderName));
    }

    @Test
    @DisplayName("파일 시스템에 이미 유저가 소유한 폴더 중 같은 이름의 폴더가 존재한다면 IllegalArgumentException을 던진다.")
    void userCanNotRenameIfExistSameFolderNameInFileSystem() {
        // given
        User user = mock(User.class);
        Folder folder = mock(Folder.class);
        Long folderId = 1L;
        String newFolderName = "newFolderName";
        when(folderQueryPort.findByUserAndName(user, newFolderName)).thenReturn(Optional.empty());
        when(folderSearchService.findById(user, folderId)).thenReturn(folder);
        File file = mock(File.class);
        when(folder.getPath()).thenReturn("path");
        when(fileFactory.createFile(folder.getPath())).thenReturn(file);
        File existingFolder = mock(File.class);
        when(file.getParent()).thenReturn("parent");
        when(fileFactory.createFile("parent" + File.separator + newFolderName)).thenReturn(existingFolder);
        when(existingFolder.exists()).thenReturn(true);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> renameFolderService.renameFolder(user, folderId, newFolderName));
    }

    @Test
    @DisplayName("파일 시스템에서 폴더의 이름을 변경하는 것에 실패했다면 IllegalArgumentException을 던진다.")
    void userCanNotRenameIfFailToRenameAtFileSystem() {
        // given
        User user = mock(User.class);
        Folder folder = mock(Folder.class);
        Long folderId = 1L;
        String newFolderName = "newFolderName";
        when(folderQueryPort.findByUserAndName(user, newFolderName)).thenReturn(Optional.empty());
        when(folderSearchService.findById(user, folderId)).thenReturn(folder);
        File file = mock(File.class);
        when(folder.getPath()).thenReturn("path");
        when(fileFactory.createFile(folder.getPath())).thenReturn(file);
        File existingFolder = mock(File.class);
        when(file.getParent()).thenReturn("parent");
        when(fileFactory.createFile("parent" + File.separator + newFolderName)).thenReturn(existingFolder);
        when(existingFolder.exists()).thenReturn(false);
        when(file.renameTo(existingFolder)).thenReturn(false);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> renameFolderService.renameFolder(user, folderId, newFolderName));
    }

}
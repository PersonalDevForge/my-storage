package org.c4marathon.assignment.file.application.service;

import org.c4marathon.assignment.file.application.service.component.FileFactory;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.application.port.in.UpdateSummaryUseCase;
import org.c4marathon.assignment.folder.application.service.FolderSearchService;
import org.c4marathon.assignment.folder.domain.entity.Folder;
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
class MoveFileServiceTest {

    @Mock
    private FileSearchService fileSearchService;

    @Mock
    private FolderSearchService folderSearchService;

    @Mock
    private UpdateSummaryUseCase updateSummaryUseCase;

    @Mock
    private FileFactory fileFactory;

    @InjectMocks
    private MoveFileService moveFileService;

    @Test
    @DisplayName("파일을 루트 폴더에서 다른 폴더로 이동시킬 수 있다.")
    void moveFileRootToFolder() {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        Folder originFolder = null;
        Folder destFolder = Folder.of(1L, user, null, "destFolder", "src/test/resources/destFolder", 0L, 0L, 0L);
        File file = File.of(1L, user, originFolder, "src/test/resources/uuid.file", "uuid", "filename", "file", 10L);
        String expectedPath = destFolder.getPath() + "/" + file.getUuid() + "." + file.getType();
        // mock
        java.io.File mockedOriginFile = mock(java.io.File.class);
        java.io.File mockedDestFile = mock(java.io.File.class);
        when(fileSearchService.getFile(user, file.getId())).thenReturn(file);
        when(folderSearchService.findById(user, destFolder.getId())).thenReturn(destFolder);
        when(fileFactory.createFile(file.getPath())).thenReturn(mockedOriginFile);
        when(fileFactory.createFile(expectedPath)).thenReturn(mockedDestFile);
        when(mockedDestFile.exists()).thenReturn(false);
        when(mockedOriginFile.exists()).thenReturn(true);
        when(mockedOriginFile.renameTo(mockedDestFile)).thenReturn(true);

        // when
        moveFileService.moveFile(user, file.getId(), destFolder.getId());

        // then
        assertEquals(file.getFolder(), destFolder);
        assertEquals(file.getPath(), destFolder.getPath() + "/" + file.getUuid() + "." + file.getType());
    }

    @Test
    @DisplayName("파일을 다른 폴더에서 루트 폴더로 이동시킬 수 있다.")
    void moveFileFolderToRoot() {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        Folder originFolder = Folder.of(1L, user, null, "destFolder", "src/test/resources/destFolder", 0L, 0L, 0L);
        Folder destFolder = null;
        File file = File.of(1L, user, originFolder, "src/test/resources/uuid.file", "uuid", "filename", "file", 10L);
        String expectedPath = "src/main/resources/upload/" + user.getEmail() + "/" + file.getUuid() + "." + file.getType();
        // mock
        java.io.File mockedOriginFile = mock(java.io.File.class);
        java.io.File mockedDestFile = mock(java.io.File.class);
        when(fileSearchService.getFile(user, file.getId())).thenReturn(file);
        when(fileFactory.createFile(file.getPath())).thenReturn(mockedOriginFile);
        when(fileFactory.createFile(expectedPath)).thenReturn(mockedDestFile);
        when(mockedDestFile.exists()).thenReturn(false);
        when(mockedOriginFile.exists()).thenReturn(true);
        when(mockedOriginFile.renameTo(mockedDestFile)).thenReturn(true);

        // when
        moveFileService.moveFile(user, file.getId(), null);

        // then
        assertEquals(file.getFolder(), null);
        assertEquals(file.getPath(),  "src/main/resources/upload/" + user.getEmail() + "/" + file.getUuid() + "." + file.getType());
    }

    @Test
    @DisplayName("목표한 폴더에 같은 이름의 파일이 이미 존재하면 파일을 이동할 수 없다.")
    void moveFileFailDestFileAlreadyExist() {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        Folder originFolder = null;
        Folder destFolder = Folder.of(1L, user, null, "destFolder", "src/test/resources/destFolder", 0L, 0L, 0L);
        File file = File.of(1L, user, originFolder, "src/test/resources/uuid.file", "uuid", "filename", "file", 10L);
        String expectedPath = destFolder.getPath() + "/" + file.getUuid() + "." + file.getType();
        // mock
        java.io.File mockedOriginFile = mock(java.io.File.class);
        java.io.File mockedDestFile = mock(java.io.File.class);
        when(fileSearchService.getFile(user, file.getId())).thenReturn(file);
        when(folderSearchService.findById(user, destFolder.getId())).thenReturn(destFolder);
        when(fileFactory.createFile(file.getPath())).thenReturn(mockedOriginFile);
        when(fileFactory.createFile(expectedPath)).thenReturn(mockedDestFile);
        when(mockedDestFile.exists()).thenReturn(true);

        // when & throw
        assertThrows(IllegalArgumentException.class, () -> {
            moveFileService.moveFile(user, file.getId(), destFolder.getId());
        });
    }

    @Test
    @DisplayName("옮기려고 시도한 파일이 실제 파일 시스템에 존재하지 않는다면 파일을 이동할 수 없다.")
    void moveFileFailOriginFileNotExist() {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        Folder originFolder = null;
        Folder destFolder = Folder.of(1L, user, null, "destFolder", "src/test/resources/destFolder", 0L, 0L, 0L);
        File file = File.of(1L, user, originFolder, "src/test/resources/uuid.file", "uuid", "filename", "file", 10L);
        String expectedPath = destFolder.getPath() + "/" + file.getUuid() + "." + file.getType();
        // mock
        java.io.File mockedOriginFile = mock(java.io.File.class);
        java.io.File mockedDestFile = mock(java.io.File.class);
        when(fileSearchService.getFile(user, file.getId())).thenReturn(file);
        when(folderSearchService.findById(user, destFolder.getId())).thenReturn(destFolder);
        when(fileFactory.createFile(file.getPath())).thenReturn(mockedOriginFile);
        when(fileFactory.createFile(expectedPath)).thenReturn(mockedDestFile);
        when(mockedDestFile.exists()).thenReturn(false);
        when(mockedOriginFile.exists()).thenReturn(false);

        // when & throw
        assertThrows(IllegalArgumentException.class, () -> {
            moveFileService.moveFile(user, file.getId(), destFolder.getId());
        });
    }

    @Test
    @DisplayName("파일을 옮기는데 실패했다면 반영되지 않는다.")
    void moveFileFailForRenameToDestFile() {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        Folder originFolder = null;
        Folder destFolder = Folder.of(1L, user, null, "destFolder", "src/test/resources/destFolder", 0L, 0L, 0L);
        File file = File.of(1L, user, originFolder, "src/test/resources/uuid.file", "uuid", "filename", "file", 10L);
        String expectedPath = destFolder.getPath() + "/" + file.getUuid() + "." + file.getType();
        // mock
        java.io.File mockedOriginFile = mock(java.io.File.class);
        java.io.File mockedDestFile = mock(java.io.File.class);
        when(fileSearchService.getFile(user, file.getId())).thenReturn(file);
        when(folderSearchService.findById(user, destFolder.getId())).thenReturn(destFolder);
        when(fileFactory.createFile(file.getPath())).thenReturn(mockedOriginFile);
        when(fileFactory.createFile(expectedPath)).thenReturn(mockedDestFile);
        when(mockedDestFile.exists()).thenReturn(false);
        when(mockedOriginFile.exists()).thenReturn(true);
        when(mockedOriginFile.renameTo(mockedDestFile)).thenReturn(false);

        // when & throw
        assertThrows(IllegalArgumentException.class, () -> {
            moveFileService.moveFile(user, file.getId(), destFolder.getId());
        });
    }

}
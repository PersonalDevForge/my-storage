package org.c4marathon.assignment.file.application.service;

import org.c4marathon.assignment.file.application.port.out.FileCommandPort;
import org.c4marathon.assignment.file.application.port.out.FileQueryPort;
import org.c4marathon.assignment.file.application.service.component.FileFactory;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.application.port.in.UpdateSummaryUseCase;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.thumbnail.application.port.in.GenerateThumbnailUseCase;
import org.c4marathon.assignment.user.application.port.in.AddUsageUseCase;
import org.c4marathon.assignment.user.application.port.in.GetUserStorageUseCase;
import org.c4marathon.assignment.user.domain.entity.User;
import org.c4marathon.assignment.user.domain.entity.UserStorage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CopyFileServiceTest {

    @Mock
    private FileSearchService fileSearchService;

    @Mock
    private FileCommandPort fileCommandPort;

    @Mock
    private FileQueryPort fileQueryPort;

    @Mock
    private UpdateSummaryUseCase updateSummaryUseCase;

    @Mock
    private GetUserStorageUseCase getUserStorageUseCase;

    @Mock
    private AddUsageUseCase addUsageUseCase;

    @Mock
    private GenerateThumbnailUseCase generateThumbnailUseCase;

    @Mock
    private FileFactory fileFactory;

    @InjectMocks
    private CopyFileService copyFileService;

    @Test
    @DisplayName("사용자가 파일을 복사할 수 있다.")
    void userCanCopyFile() {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        String fileName = "test.jpg";  // 이미지 파일 확장자
        Folder folder = Folder.of(1L, user, null, "folderName", "path", 0L, 0L, 0L);
        File file = File.of(1L, user, folder, "path", "uuid", fileName, "jpg", 50L);
        UserStorage userStorage = UserStorage.of(1L, user, 100L, 0L);

        // mock
        when(fileSearchService.getFile(user, 1L)).thenReturn(file);
        when(fileQueryPort.findByUserAndFileNameAndFolder(user, fileName + "- Copy.jpg", folder)).thenReturn(Optional.empty());
        when(getUserStorageUseCase.getUserStorage(user.getId())).thenReturn(userStorage);

        // when & then
        assertDoesNotThrow(() -> copyFileService.copyFile(user, 1L));
    }

    @Test
    @DisplayName("사용자는 타입이 없는 파일을 복사할 수 있다.")
    void userCanCopyFileThatDoesNotHaveType() {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        String fileName = "test";
        Folder folder = Folder.of(1L, user, null, "folderName", "path", 0L, 0L, 0L);
        File file = File.of(1L, user, folder, "path", "uuid", fileName, "", 50L);
        UserStorage userStorage = UserStorage.of(1L, user, 100L, 0L);

        // mock
        when(fileSearchService.getFile(user, 1L)).thenReturn(file);
        when(fileQueryPort.findByUserAndFileNameAndFolder(user, fileName + "- Copy", folder)).thenReturn(Optional.empty());
        when(getUserStorageUseCase.getUserStorage(user.getId())).thenReturn(userStorage);

        // when & then
        assertDoesNotThrow(() -> copyFileService.copyFile(user, 1L));
    }

    @Test
    @DisplayName("복사하려는 파일이 유저의 남은 저장 용량을 초과하면 복사할 수 없다.")
    void userCanNotCopyFileIfStorageCapacityExceed() {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        String fileName = "test.jpg";  // 이미지 파일 확장자
        Folder folder = Folder.of(1L, user, null, "folderName", "path", 0L, 0L, 0L);
        File file = File.of(1L, user, folder, "path", "uuid", fileName, "jpg", 50L);
        UserStorage userStorage = UserStorage.of(1L, user, 100L, 51L);

        // mock
        when(fileSearchService.getFile(user, 1L)).thenReturn(file);
        when(fileQueryPort.findByUserAndFileNameAndFolder(user, fileName + "- Copy.jpg", folder)).thenReturn(Optional.empty());
        when(getUserStorageUseCase.getUserStorage(user.getId())).thenReturn(userStorage);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> copyFileService.copyFile(user, 1L));
    }

    @Test
    @DisplayName("복사하려는 파일이 들어있는 폴더 내에 '{파일이름} - Copy' 파일이 이미 존재하면 ' - Copy'를 재귀적으로 붙인다.")
    void ifCopiedFileNameDuplicatedThenAddCopyToFileName() {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        String fileName = "test.jpg";  // 이미지 파일 확장자
        Folder folder = Folder.of(1L, user, null, "folderName", "path", 0L, 0L, 0L);
        File file = File.of(1L, user, folder, "path", "uuid", fileName, "jpg", 50L);
        File duplicatedFile = File.of(2L, user, folder, "path", "uuid", fileName + " - Copy.jpg", "jpg", 50L);
        UserStorage userStorage = UserStorage.of(1L, user, 100L, 0L);

        // mock
        when(fileSearchService.getFile(user, 1L)).thenReturn(file);
        when(fileQueryPort.findByUserAndFileNameAndFolder(user, "test - Copy.jpg", folder)).thenReturn(Optional.of(duplicatedFile));
        when(getUserStorageUseCase.getUserStorage(user.getId())).thenReturn(userStorage);

        // when & then
        assertDoesNotThrow(() -> copyFileService.copyFile(user, 1L));
    }

    @Test
    @DisplayName("파일 시스템에서 파일을 복사하는 것에 실패한다면 IllegalArgumentException을 던진다.")
    void userCanNotCopyFileIfFailToCopyFileAtFileSystem() throws IOException {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        String fileName = "test.jpg";  // 이미지 파일 확장자
        Folder folder = Folder.of(1L, user, null, "folderName", "path", 0L, 0L, 0L);
        File file = File.of(1L, user, folder, "path", "uuid", fileName, "jpg", 50L);
        UserStorage userStorage = UserStorage.of(1L, user, 100L, 0L);

        // mock
        when(fileSearchService.getFile(user, 1L)).thenReturn(file);
        when(fileQueryPort.findByUserAndFileNameAndFolder(user, fileName + "- Copy.jpg", folder)).thenReturn(Optional.empty());
        when(getUserStorageUseCase.getUserStorage(user.getId())).thenReturn(userStorage);
        when(fileFactory.copyFile(any(), any())).thenThrow(new IOException("Failed to copy file"));

        // when & then
        assertThrows(IllegalArgumentException.class, () -> copyFileService.copyFile(user, 1L));
    }

}
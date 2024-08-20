package org.c4marathon.assignment.file.application.service;

import org.c4marathon.assignment.file.application.port.out.FileCommandPort;
import org.c4marathon.assignment.file.application.service.component.FileFactory;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.application.port.in.UpdateSummaryUseCase;
import org.c4marathon.assignment.thumbnail.application.port.in.DeleteThumbnailUseCase;
import org.c4marathon.assignment.user.application.port.in.AddUsageUseCase;
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
class DeleteFileServiceTest {

    @Mock
    private FileSearchService fileSearchService;

    @Mock
    private FileCommandPort fileCommandPort;

    @Mock
    private UpdateSummaryUseCase updateSummaryUseCase;

    @Mock
    private AddUsageUseCase addUsageUseCase;

    @Mock
    private DeleteThumbnailUseCase deleteThumbnailUseCase;

    @Mock
    private FileFactory fileFactory;

    @InjectMocks
    private DeleteFileService deleteFileService;

    @Test
    @DisplayName("유저는 fileId에 해당하는 파일을 삭제할 수 있다.")
    void userCanDeleteFile() {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        Long fileId = 1L;
        File file = File.of(1L, user, null, "src/test/resources/uuid.txt", "uuid", "test.txt", "txt", 100L);
        java.io.File diskFile = mock(java.io.File.class);
        when(diskFile.delete()).thenReturn(true);
        when(fileFactory.createFile(file.getPath())).thenReturn(diskFile);
        when(fileSearchService.getFile(user, fileId)).thenReturn(file);

        // when & then
        assertDoesNotThrow(() -> deleteFileService.deleteFile(user, fileId));
    }

    @Test
    @DisplayName("유저가 파일 시스템에 존재하지 않는 파일을 삭제하려고 시도하면 RuntimeException을 던진다.")
    void userCanNotDeleteIfFileNotExist() {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        Long fileId = 1L;
        File file = File.of(1L, user, null, "src/test/resources/uuid.txt", "uuid", "test.txt", "txt", 100L);
        java.io.File diskFile = mock(java.io.File.class);
        when(diskFile.delete()).thenReturn(false);
        when(fileFactory.createFile(file.getPath())).thenReturn(diskFile);
        when(fileSearchService.getFile(user, fileId)).thenReturn(file);

        // when & then
        assertThrows(RuntimeException.class, () -> deleteFileService.deleteFile(user, fileId));
    }

}
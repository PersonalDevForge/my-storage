package org.c4marathon.assignment.file.application.service;

import org.c4marathon.assignment.file.application.port.in.WriteToFileSystemUseCase;
import org.c4marathon.assignment.file.application.port.out.FileCommandPort;
import org.c4marathon.assignment.file.application.port.out.FileQueryPort;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.application.port.in.UpdateSummaryUseCase;
import org.c4marathon.assignment.folder.application.service.FolderSearchService;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.thumbnail.application.port.in.GenerateThumbnailUseCase;
import org.c4marathon.assignment.user.application.port.in.AddUsageUseCase;
import org.c4marathon.assignment.user.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UploadFileServiceTest {

    @Mock
    private FileQueryPort fileQueryPort;

    @Mock
    private FileCommandPort fileCommandPort;

    @Mock
    private WriteToFileSystemUseCase writeToFileSystemUseCase;

    @Mock
    private FolderSearchService folderSearchService;

    @Mock
    private UpdateSummaryUseCase updateSummaryUseCase;

    @Mock
    private AddUsageUseCase addUsageUseCase;

    @Mock
    private GenerateThumbnailUseCase generateThumbnailUseCase;

    @InjectMocks
    private UploadFileService uploadFileService;

    @Test
    @DisplayName("이미지 파일인 경우 썸네일을 생성한다.")
    void uploadImageFileGeneratesThumbnail() {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        String fileName = "test.jpg";  // 이미지 파일 확장자
        Long folderId = 1L;
        byte[] file = new byte[0];
        Folder folder = Folder.of(user, null, "folderName", "path", 0L, 0L, 0L);
        File metaData = File.of(user, folder, "path", "uuid", fileName, "jpg", 10L);

        when(folderSearchService.findById(user, folderId)).thenReturn(folder);
        when(fileQueryPort.findByUserAndFileNameAndFolder(user, fileName, folder)).thenReturn(Optional.empty());
        when(fileCommandPort.save(any(File.class))).thenReturn(metaData);

        // when
        uploadFileService.uploadFile(user, fileName, folderId, file);

        // then
        verify(generateThumbnailUseCase, times(1)).generateThumbnail(metaData);
    }

    @Test
    @DisplayName("이미지 파일이 아닌 경우 썸네일을 생성하지 않는다.")
    void uploadNotImageFileDoNotGeneratesThumbnail() {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        String fileName = "test.notImage";
        Long folderId = 1L;
        byte[] file = new byte[0];
        Folder folder = Folder.of(user, null, "folderName", "path", 0L, 0L, 0L);
        File metaData = File.of(user, folder, "path", "uuid", fileName, "notImage", 10L);

        when(folderSearchService.findById(user, folderId)).thenReturn(folder);
        when(fileQueryPort.findByUserAndFileNameAndFolder(user, fileName, folder)).thenReturn(Optional.empty());
        when(fileCommandPort.save(any(File.class))).thenReturn(metaData);

        // when
        uploadFileService.uploadFile(user, fileName, folderId, file);

        // then
        verify(generateThumbnailUseCase, times(0)).generateThumbnail(metaData);
    }

    @Test
    @DisplayName("확장자가 이름에 없는 경우 확장자로 빈 문자열을 리턴한다.")
    void invalidExtension() {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        String fileName = "test";
        Long folderId = 1L;
        byte[] file = new byte[0];
        Folder folder = Folder.of(user, null, "folderName", "path", 0L, 0L, 0L);
        File metaData = File.of(user, folder, "path", "uuid", fileName, "notImage", 10L);

        when(folderSearchService.findById(user, folderId)).thenReturn(folder);
        when(fileQueryPort.findByUserAndFileNameAndFolder(user, fileName, folder)).thenReturn(Optional.empty());
        when(fileCommandPort.save(any(File.class))).thenReturn(metaData);

        // when
        uploadFileService.uploadFile(user, fileName, folderId, file);

        // then
        verify(generateThumbnailUseCase, times(0)).generateThumbnail(metaData);
    }

    @Test
    @DisplayName("Folder가 null인 경우, 루트 폴더에 파일을 업로드한다.")
    void uploadFileToRootFolder() {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        String fileName = "test.file";
        Long folderId = 1L;
        byte[] file = new byte[0];
        Folder folder = null;
        File metaData = File.of(user, null, "path", "uuid", fileName, "notImage", 10L);

        when(folderSearchService.findById(user, folderId)).thenReturn(folder);
        when(fileQueryPort.findByUserAndFileNameAndFolder(user, fileName, folder)).thenReturn(Optional.empty());
        when(fileCommandPort.save(any(File.class))).thenReturn(metaData);

        // when
        uploadFileService.uploadFile(user, fileName, folderId, file);

        // then
        verify(generateThumbnailUseCase, times(0)).generateThumbnail(metaData);
    }

    @Test
    @DisplayName("메타 데이터 상에서, 같은 폴더에 같은 이름의 파일이 이미 존재하는 경우 IllegalArgumentException을 던진다.")
    void uploadFileAlreadyExistFileNameInMetaData() {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        String fileName = "test.jpg";
        Long folderId = 1L;
        byte[] file = new byte[0];
        Folder folder = Folder.of(user, null, "folderName", "path", 0L, 0L, 0L);
        File alreadyExistFile = File.of(user, folder, "path", "uuid", "test.jpg", "jpg", 10L);
        when(folderSearchService.findById(user, folderId)).thenReturn(folder);
        when(fileQueryPort.findByUserAndFileNameAndFolder(user, fileName, folder)).thenReturn(Optional.of(alreadyExistFile));

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            uploadFileService.uploadFile(user, fileName, folderId, file);
        });

    }

}
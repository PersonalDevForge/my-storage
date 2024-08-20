package org.c4marathon.assignment.thumbnail.application.service;

import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.thumbnail.application.port.out.ThumbnailQueryPort;
import org.c4marathon.assignment.thumbnail.domain.entity.Thumbnail;
import org.c4marathon.assignment.user.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class})
class SearchThumbnailServiceTest {

    @Mock
    private ThumbnailQueryPort thumbnailQueryPort;

    @InjectMocks
    private SearchThumbnailService searchThumbnailService;

    @Test
    @DisplayName("파일 아이디로 파일의 썸네일을 조회할 수 있다.")
    void getThumbnailIfThumbnailExist() {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        File file = File.of(1L, user, null, "src/test/resources/uuid.jpg", "uuid","test.jpg", "jpg", 100L);
        Thumbnail thumbnail = Thumbnail.of(1L, file, null, "uuid", "jpg", "src/test/resources/uuid.jpg", 100L);
        when(thumbnailQueryPort.findByFileId(1L)).thenReturn(Optional.of(thumbnail));

        // when
        Thumbnail result = searchThumbnailService.getThumbnail(1L);

        // then
        assertEquals(thumbnail, result);
    }

    @Test
    @DisplayName("썸네일이 존재하지 않는 파일이라면 예외를 던지지 않고, null을 반환한다.")
    void getThumbnailIfThumbnailNotExist() {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        File file = File.of(1L, user, null, "src/test/resources/uuid.txt", "uuid","test.txt", "txt", 100L);
        when(thumbnailQueryPort.findByFileId(1L)).thenReturn(Optional.empty());

        // when
        Thumbnail result = searchThumbnailService.getThumbnail(1L);

        // then
        assertNull(result);
    }

    @Test
    @DisplayName("특정 폴더안에 존재하는 모든 썸네일을 조회할 수 있다.")
    void getAllThumbnailInFolder() {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        Folder folder = Folder.of(1L, user, null, "folder", "src/test/resources/folder", 0L, 2L, 200L);
        File file1 = File.of(1L, user, folder, "src/test/resources/uuid1.jpg", "uuid1","test1.jpg", "jpg", 100L);
        File file2 = File.of(2L, user, folder, "src/test/resources/uuid2.jpg", "uuid2","test2.jpg", "jpg", 100L);
        Thumbnail thumbnail1 = Thumbnail.of(1L, file1, null, "uuid11", "jpg", "src/test/resources/uuid11.jpg", 100L);
        Thumbnail thumbnail2 = Thumbnail.of(2L, file2, null, "uuid22", "jpg", "src/test/resources/uuid22.jpg", 100L);
        List <Thumbnail> thumbnails = List.of(thumbnail1, thumbnail2);
        when(thumbnailQueryPort.findAllByFolderId(1L)).thenReturn(thumbnails);

        // when
        List<Thumbnail> result = searchThumbnailService.getAllThumbnailInFolder(1L);

        // then
        assertEquals(thumbnails, result);
    }

}
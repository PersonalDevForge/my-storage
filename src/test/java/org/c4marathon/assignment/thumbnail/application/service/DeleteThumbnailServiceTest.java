package org.c4marathon.assignment.thumbnail.application.service;

import org.c4marathon.assignment.file.application.service.component.FileFactory;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.thumbnail.application.port.out.ThumbnailCommandPort;
import org.c4marathon.assignment.thumbnail.application.port.out.ThumbnailQueryPort;
import org.c4marathon.assignment.thumbnail.domain.entity.Thumbnail;
import org.c4marathon.assignment.user.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.c4marathon.assignment.tools.TestTool.createFile;
import static org.c4marathon.assignment.tools.TestTool.createUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class DeleteThumbnailServiceTest {

    @Mock
    private ThumbnailQueryPort thumbnailQueryPort;

    @Mock
    private ThumbnailCommandPort thumbnailCommandPort;

    @Mock
    private FileFactory fileFactory;

    @InjectMocks
    private DeleteThumbnailService deleteThumbnailService;

    @Test
    @DisplayName("파일의 아이디를 이용하여 썸네일을 삭제할 수 있다.")
    void deleteThumbnailByFileId() {
        // given
        User user = createUser();
        File file = createFile(user);

        // mock
        Thumbnail thumbnail = mock(Thumbnail.class);
        Path path = mock(Path.class);
        when(thumbnailQueryPort.findByFileId(file.getId())).thenReturn(Optional.of(thumbnail));
        when(fileFactory.createPath(thumbnail.getPath())).thenReturn(path);

        // when & then
        assertDoesNotThrow(() -> deleteThumbnailService.deleteThumbnail(file.getId()));
    }

    @Test
    @DisplayName("썸네일이 존재하지 않는다면 아무일도 하지 않는다.")
    void deleteThumbnailDoNothingIfThumbnailDoesNotExist() {
        // given
        User user = createUser();
        File file = createFile(user);

        // mock
        when(thumbnailQueryPort.findByFileId(file.getId())).thenReturn(Optional.empty());

        // when & then
        assertDoesNotThrow(() -> deleteThumbnailService.deleteThumbnail(file.getId()));
    }


    @Test
    @DisplayName("한 폴더안에 있는 모든 썸네일을 삭제할 수 있다.")
    void deleteAllThumbnailInFolder() {
        // given
        User user = createUser();
        File file = createFile(user);
        Long folderId = null;

        // mock
        Thumbnail thumbnail1 = mock(Thumbnail.class);
        Thumbnail thumbnail2 = mock(Thumbnail.class);
        List<Thumbnail> mockedThumbnailList = List.of(thumbnail1, thumbnail2);
        Path path = mock(Path.class);
        when(thumbnailQueryPort.findAllByFolderId(folderId)).thenReturn(mockedThumbnailList);
        when(fileFactory.createPath(thumbnail1.getPath())).thenReturn(path);
        when(fileFactory.createPath(thumbnail2.getPath())).thenReturn(path);

        // when & then
        assertDoesNotThrow(() -> deleteThumbnailService.deleteAllThumbnailInFolder(file.getId()));
    }

}
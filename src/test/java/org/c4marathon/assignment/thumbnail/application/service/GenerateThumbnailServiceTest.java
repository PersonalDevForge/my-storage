package org.c4marathon.assignment.thumbnail.application.service;

import org.c4marathon.assignment.file.application.service.component.FileFactory;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.thumbnail.application.port.out.ThumbnailCommandPort;
import org.c4marathon.assignment.thumbnail.application.service.component.ImageFactory;
import org.c4marathon.assignment.thumbnail.application.service.component.WriteImage;
import org.c4marathon.assignment.user.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class GenerateThumbnailServiceTest {

    @Mock
    private ThumbnailCommandPort thumbnailCommandPort;

    @Mock
    private FileFactory fileFactory;

    @Mock
    private ImageFactory imageFactory;

    @Mock
    private WriteImage writeImage;

    @InjectMocks
    private GenerateThumbnailService generateThumbnailService;

    @Test
    @DisplayName("파일의 종류가 이미지인 경우 썸네일을 생성한다.")
    void generateThumbnailWhenFileIsImage() throws IOException {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        File file = File.of(1L, user, null, "src/test/resources/uuid.jpg", "uuid","test.jpg", "jpg", 100L);

        // mock
        java.io.File mockedOriginImage = mock(java.io.File.class);
        java.io.File mockedThumbnailImage = mock(java.io.File.class);
        java.io.File mockedRootDirectory = mock(java.io.File.class);
        BufferedImage mockedBOImage = mock(BufferedImage.class);
        BufferedImage mockedBTImage = mock(BufferedImage.class);
        Graphics2D mockedGraphics = mock(Graphics2D.class);
        when(fileFactory.createFile(null)).thenReturn(mockedRootDirectory);
        when(mockedRootDirectory.exists()).thenReturn(false);
        when(mockedRootDirectory.mkdirs()).thenReturn(true);
        when(fileFactory.createFile(file.getPath())).thenReturn(mockedOriginImage);
        when(imageFactory.read(any())).thenReturn(mockedBOImage);
        when(imageFactory.createBufferedImage(300, 300, BufferedImage.TYPE_3BYTE_BGR)).thenReturn(mockedBTImage);
        when(imageFactory.createGraphics(mockedBTImage)).thenReturn(mockedGraphics);
        when(fileFactory.createFile(anyString())).thenReturn(mockedThumbnailImage);
        when(mockedThumbnailImage.length()).thenReturn(100L);

        // when & then
        assertDoesNotThrow(() -> generateThumbnailService.generateThumbnail(file));
    }

    @Test
    @DisplayName("이미지를 여는 것에 실패해서 예외를 던지고, 썸네일 생성에 실패했다면 빈 문자열을 반환한다.")
    void returnEmptyPathStringWhenFailToOpenImageCauseThrowIOException() throws IOException {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        File file = File.of(1L, user, null, "src/test/resources/uuid.jpg", "uuid","test.jpg", "jpg", 100L);

        // mock
        java.io.File mockedOriginImage = mock(java.io.File.class);
        java.io.File mockedThumbnailImage = mock(java.io.File.class);
        java.io.File mockedRootDirectory = mock(java.io.File.class);
        BufferedImage mockedBTImage = mock(BufferedImage.class);
        Graphics2D mockedGraphics = mock(Graphics2D.class);
        when(fileFactory.createFile(null)).thenReturn(mockedRootDirectory);
        when(mockedRootDirectory.exists()).thenReturn(false);
        when(mockedRootDirectory.mkdirs()).thenReturn(true);
        when(fileFactory.createFile(file.getPath())).thenReturn(mockedOriginImage);
        when(imageFactory.read(any())).thenThrow(new IOException());
        when(imageFactory.createBufferedImage(300, 300, BufferedImage.TYPE_3BYTE_BGR)).thenReturn(mockedBTImage);
        when(imageFactory.createGraphics(mockedBTImage)).thenReturn(mockedGraphics);
        when(fileFactory.createFile(anyString())).thenReturn(mockedThumbnailImage);
        when(mockedThumbnailImage.length()).thenReturn(100L);

        // when
        String emptyPath = generateThumbnailService.generateThumbnail(file);

        // then
        assertEquals("", emptyPath);
    }

    @Test
    @DisplayName("이미지를 여는 것에 실패해서 null을 리턴하고, 썸네일 생성에 실패했다면 빈 문자열을 반환한다.")
    void returnEmptyPathStringWhenFailToOpenImageCauseReturnNull() throws IOException {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        File file = File.of(1L, user, null, "src/test/resources/uuid.jpg", "uuid","test.jpg", "jpg", 100L);

        // mock
        java.io.File mockedOriginImage = mock(java.io.File.class);
        java.io.File mockedThumbnailImage = mock(java.io.File.class);
        java.io.File mockedRootDirectory = mock(java.io.File.class);
        BufferedImage mockedBTImage = mock(BufferedImage.class);
        Graphics2D mockedGraphics = mock(Graphics2D.class);
        when(fileFactory.createFile(null)).thenReturn(mockedRootDirectory);
        when(mockedRootDirectory.exists()).thenReturn(false);
        when(mockedRootDirectory.mkdirs()).thenReturn(true);
        when(fileFactory.createFile(file.getPath())).thenReturn(mockedOriginImage);
        when(imageFactory.read(any())).thenReturn(null);
        when(imageFactory.createBufferedImage(300, 300, BufferedImage.TYPE_3BYTE_BGR)).thenReturn(mockedBTImage);
        when(imageFactory.createGraphics(mockedBTImage)).thenReturn(mockedGraphics);
        when(fileFactory.createFile(anyString())).thenReturn(mockedThumbnailImage);
        when(mockedThumbnailImage.length()).thenReturn(100L);

        // when
        String emptyPath = generateThumbnailService.generateThumbnail(file);

        // then
        assertEquals("", emptyPath);
    }

    @Test
    @DisplayName("파일 시스템 상에 공간이 부족해서 썸네일 생성에 실패했다면 빈 문자열을 반환한다.")
    void returnEmptyPathStringWhenFailToGenerateThumbnailCauseByWriteImage() throws IOException {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        File file = File.of(1L, user, null, "src/test/resources/uuid.jpg", "uuid","test.jpg", "jpg", 100L);

        // mock
        java.io.File mockedOriginImage = mock(java.io.File.class);
        java.io.File mockedThumbnailImage = mock(java.io.File.class);
        java.io.File mockedRootDirectory = mock(java.io.File.class);
        BufferedImage mockedBOImage = mock(BufferedImage.class);
        BufferedImage mockedBTImage = mock(BufferedImage.class);
        Graphics2D mockedGraphics = mock(Graphics2D.class);
        when(fileFactory.createFile(null)).thenReturn(mockedRootDirectory);
        when(mockedRootDirectory.exists()).thenReturn(false);
        when(mockedRootDirectory.mkdirs()).thenReturn(true);
        when(fileFactory.createFile(file.getPath())).thenReturn(mockedOriginImage);
        when(imageFactory.read(any())).thenReturn(mockedBOImage);
        when(imageFactory.createBufferedImage(300, 300, BufferedImage.TYPE_3BYTE_BGR)).thenReturn(mockedBTImage);
        when(imageFactory.createGraphics(mockedBTImage)).thenReturn(mockedGraphics);
        when(fileFactory.createFile(anyString())).thenReturn(mockedThumbnailImage);
        when(writeImage.writeImage(mockedBTImage, "jpg", mockedThumbnailImage)).thenThrow(new IOException());

        // when
        String emptyPath = generateThumbnailService.generateThumbnail(file);

        // then
        assertEquals("", emptyPath);
    }


    @Test
    @DisplayName("썸네일 이미지가 저장되는 디렉토리 생성에 실패했다면 RuntimeException을 던진다.")
    void failToGenerateRootDirectory() throws IOException {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        File file = File.of(1L, user, null, "src/test/resources/uuid.jpg", "uuid","test.jpg", "jpg", 100L);

        // mock
        java.io.File mockedOriginImage = mock(java.io.File.class);
        java.io.File mockedThumbnailImage = mock(java.io.File.class);
        java.io.File mockedRootDirectory = mock(java.io.File.class);
        BufferedImage mockedBOImage = mock(BufferedImage.class);
        BufferedImage mockedBTImage = mock(BufferedImage.class);
        Graphics2D mockedGraphics = mock(Graphics2D.class);
        when(fileFactory.createFile(null)).thenReturn(mockedRootDirectory);
        when(mockedRootDirectory.exists()).thenReturn(false);
        when(mockedRootDirectory.mkdirs()).thenReturn(false);
        when(fileFactory.createFile(file.getPath())).thenReturn(mockedOriginImage);
        when(imageFactory.read(any())).thenReturn(mockedBOImage);
        when(imageFactory.createBufferedImage(300, 300, BufferedImage.TYPE_3BYTE_BGR)).thenReturn(mockedBTImage);
        when(imageFactory.createGraphics(mockedBTImage)).thenReturn(mockedGraphics);
        when(fileFactory.createFile(anyString())).thenReturn(mockedThumbnailImage);
        when(mockedThumbnailImage.length()).thenReturn(100L);

        // when & then
        assertThrows(RuntimeException.class, () -> generateThumbnailService.generateThumbnail(file));
    }

}
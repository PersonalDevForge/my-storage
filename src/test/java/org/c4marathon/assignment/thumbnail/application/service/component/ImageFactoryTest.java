package org.c4marathon.assignment.thumbnail.application.service.component;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.c4marathon.assignment.tools.TestTool.createFile;
import static org.c4marathon.assignment.tools.TestTool.createUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ImageFactoryTest {

    @InjectMocks
    private ImageFactory imageFactory;

    @Test
    @DisplayName("createBufferedImage()는 BufferedImage를 생성한다.")
    void createBufferedImage() {
        // given
        int width = 100;
        int height = 100;
        int imageType = BufferedImage.TYPE_3BYTE_BGR;

        // when
        BufferedImage result = imageFactory.createBufferedImage(width, height, imageType);

        // then
        assertEquals(result.getWidth(), width);
        assertEquals(result.getHeight(), height);
        assertEquals(result.getType(), imageType);
    }

    @Test
    @DisplayName("read()는 파일을 읽어 BufferedImage로 반환한다.")
    void read() throws IOException {
        // given
        // mock
        File mockedFile = mock(File.class);
        BufferedImage mockedBufferedImage = mock(BufferedImage.class);
        mockStatic(ImageIO.class);
        when(ImageIO.read(mockedFile)).thenReturn(mockedBufferedImage);

        // when
        BufferedImage result = imageFactory.read(mockedFile);

        // then
        assertEquals(result, mockedBufferedImage);
    }

    @Test
    @DisplayName("createGraphics()는 Graphics2D를 생성한다.")
    void createGraphics() {
        // given
        // mock
        BufferedImage mockedBufferedImage = mock(BufferedImage.class);
        Graphics2D mockedGraphics2D = mock(Graphics2D.class);
        when(mockedBufferedImage.createGraphics()).thenReturn(mockedGraphics2D);

        // when
        Graphics2D result = imageFactory.createGraphics(mockedBufferedImage);

        // then
        assertEquals(result, mockedGraphics2D);
    }

}
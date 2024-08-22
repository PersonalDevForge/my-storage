package org.c4marathon.assignment.thumbnail.application.service.component;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class WriteImageTest {

    @InjectMocks
    private WriteImage writeImage;

    private static MockedStatic<ImageIO> imageIO;

    @BeforeAll
    public static void beforeAll() {
        imageIO = mockStatic(ImageIO.class);
    }

    @AfterAll
    public static void afterAll() {
        imageIO.close();
    }

    @Test
    @DisplayName("writeImage()는 이미지를 파일 시스템에 저장한다.")
    void writeImage() throws IOException {
        // given
        String formatName = "jpg";

        // mock
        BufferedImage mockedBufferedImage = mock(BufferedImage.class);
        File mockedFile = mock(File.class);
        when(ImageIO.write(mockedBufferedImage, formatName, mockedFile)).thenReturn(true);

        // when
        boolean result = writeImage.writeImage(mockedBufferedImage, formatName, mockedFile);

        // then
        assertTrue(result);
    }

}
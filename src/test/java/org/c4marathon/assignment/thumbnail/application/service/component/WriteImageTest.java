package org.c4marathon.assignment.thumbnail.application.service.component;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class WriteImageTest {

    @InjectMocks
    private WriteImage writeImage;

    @Test
    @DisplayName("writeImage()는 이미지를 파일 시스템에 저장한다.")
    void writeImage() throws IOException {
        // given
        String formatName = "jpg";

        // mock
        BufferedImage mockedBufferedImage = mock(BufferedImage.class);
        File mockedFile = mock(File.class);
        mockStatic(ImageIO.class);
        when(ImageIO.write(mockedBufferedImage, formatName, mockedFile)).thenReturn(true);

        // when
        boolean result = writeImage.writeImage(mockedBufferedImage, formatName, mockedFile);

        // then
        assertTrue(result);
    }

}
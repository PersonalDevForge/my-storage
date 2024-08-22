package org.c4marathon.assignment.thumbnail.application.service.component;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Component
@NoArgsConstructor
public class ImageFactory {

    public BufferedImage createBufferedImage(int width, int height, int imageType) {
        return new BufferedImage(width, height, imageType);
    }

    public BufferedImage read(File file) throws IOException {
        return ImageIO.read(file);
    }

    public Graphics2D createGraphics(BufferedImage image) {
        return image.createGraphics();
    }

}

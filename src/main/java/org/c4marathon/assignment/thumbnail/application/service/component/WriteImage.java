package org.c4marathon.assignment.thumbnail.application.service.component;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Component
@NoArgsConstructor
public class WriteImage {

    public boolean writeImage(BufferedImage image, String formatName, File outFile) throws IOException {
        return ImageIO.write(image, formatName, outFile);
    }

}

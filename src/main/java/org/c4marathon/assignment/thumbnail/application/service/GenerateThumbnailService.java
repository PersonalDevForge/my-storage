package org.c4marathon.assignment.thumbnail.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.thumbnail.application.port.in.GenerateThumbnailUseCase;
import org.c4marathon.assignment.thumbnail.application.port.out.ThumbnailCommandPort;
import org.c4marathon.assignment.thumbnail.domain.entity.Thumbnail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenerateThumbnailService implements GenerateThumbnailUseCase {

    private final ThumbnailCommandPort thumbnailCommandPort;

    @Value("${thumbnail.base-path}")
    private String basePath;

    private final int THUMBNAIL_WIDTH = 300;

    private final int THUMBNAIL_HEIGHT = 300;

    private void generateRootDirectory() {
        java.io.File rootDirectory = new java.io.File(basePath);
        if (!rootDirectory.exists()) {
            if (!rootDirectory.mkdirs()) {
                throw new RuntimeException("Failed to create directory");
            }
        }
    }

    private Long generateThumbnailFromImage(String originImagePath, String uuid, int width, int height, String extension) {
        java.io.File originImage = new java.io.File(originImagePath);
        BufferedImage bo_image, bt_image;

        generateRootDirectory();
        try {
            bo_image = ImageIO.read(originImage);
            if (bo_image == null) {
                return null;
            }
            bt_image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        } catch (Exception e) {
            return null;
        }

        Graphics2D graphic = bt_image.createGraphics();
        graphic.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphic.drawImage(bo_image, 0, 0, width, height, null);

        String path = basePath + "/" + uuid + "." + extension;
        java.io.File thumbnailImage = new java.io.File(path);
        try {
            ImageIO.write(bt_image, extension, thumbnailImage);
        } catch (Exception e) {
            graphic.dispose();
            return null;
        }

        graphic.dispose();
        return thumbnailImage.length();
    }

    @Override
    @Transactional
    public String generateThumbnail(File file) {
        String uuid = UUID.randomUUID().toString();
        String path = basePath + "/" + uuid + "." + file.getType();
        Long size = generateThumbnailFromImage(file.getPath(), uuid, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, file.getType());
        if (size == null) {
            log.info("failed to generate thumbnail.");
            return "";
        }
        Thumbnail generatedThumbnail = Thumbnail.of(null, file, file.getFolder(), uuid, file.getType(), path, size);
        thumbnailCommandPort.save(generatedThumbnail);
        return path;
    }

}

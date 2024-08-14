package org.c4marathon.assignment.thumbnail.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class GenerateThumbnailService implements GenerateThumbnailUseCase {

    private final ThumbnailCommandPort thumbnailCommandPort;

    @Value("${thumbnail.base-path}")
    private String basePath;

    private final String THUMBNAIL_EXTENSION = "jpg";

    private Long generateThumbnailFromImage(String originImagePath, String uuid, int width, int height, String extension) {
        java.io.File originImage = new java.io.File(originImagePath);
        String path = basePath + "/" + uuid + "." + THUMBNAIL_EXTENSION;
        java.io.File thumbnailImage = new java.io.File(path);
        BufferedImage bo_image, bt_image;

        try {
            bo_image = ImageIO.read(originImage);
            bt_image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid image file");
        }

        Graphics2D graphic = bt_image.createGraphics();
        graphic.drawImage(bo_image, 0, 0,300,500, null);

        try {
            ImageIO.write(bt_image, extension, thumbnailImage);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to generate thumbnail");
        }

        return 0L;
    }

    @Override
    @Transactional
    public String GenerateThumbnail(File file) {
        String uuid = UUID.randomUUID().toString();
        String path = basePath + "/" + uuid + "." + THUMBNAIL_EXTENSION;
        Long size = generateThumbnailFromImage(file.getPath(), uuid, 100, 100, THUMBNAIL_EXTENSION);
        Thumbnail generatedThumbnail = Thumbnail.of(null, file, file.getFolder(), uuid, THUMBNAIL_EXTENSION, path, size);
        thumbnailCommandPort.save(generatedThumbnail);
        return path;
    }

}

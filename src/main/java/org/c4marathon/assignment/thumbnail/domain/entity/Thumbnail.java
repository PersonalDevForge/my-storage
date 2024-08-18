package org.c4marathon.assignment.thumbnail.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.global.aop.BaseTimeEntity;

@Getter
@Entity
@Table(name = "MS_THUMBNAILS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Thumbnail extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "file_id", nullable = true)
    private File file;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "folder_id", nullable = true)
    private Folder folder;

    private String uuid;

    private String extension;

    private String path;

    private Long size;

    public static Thumbnail of(Long id, File file, Folder folder, String uuid, String extension, String path, Long size) {
        return new Thumbnail(id, file, folder, uuid, extension, path, size);
    }

    public String getThumbnailUrl() {
        if (this.extension == null || this.extension.isEmpty()) {
            return "/thumbnails/" + this.uuid;
        }
        return "/thumbnails/" + this.uuid + "." + this.extension;
    }

}

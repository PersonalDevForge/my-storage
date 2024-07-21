package org.c4marathon.assignment.file.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.user.domain.entity.User;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "MS_FILE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Folder folder;

    private String path;

    private String uuid;

    private String filename;

    private String type;

    private Long size;

    private LocalDateTime createdAt;

    private File(User user, Folder folder, String path, String uuid, String filename, String type, Long size, LocalDateTime createdAt) {
        this.id = null;
        this.user = user;
        this.folder = folder;
        this.path = path;
        this.uuid = uuid;
        this.filename = filename;
        this.type = type;
        this.size = size;
        this.createdAt = createdAt;
    }

    public static File of(User user, Folder folder, String path, String uuid, String filename, String type, Long size, LocalDateTime createdAt) {
        return new File(user, folder, path, uuid, filename, type, size, createdAt);
    }

}

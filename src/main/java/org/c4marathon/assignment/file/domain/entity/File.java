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

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Folder folder;

    @Column(unique = true)
    private String path;

    private String uuid;

    private String fileName;

    private String type;

    private Long size;

    private LocalDateTime createdAt;

    private File(User user, Folder folder, String path, String uuid, String fileName, String type, Long size, LocalDateTime createdAt) {
        this.id = null;
        this.user = user;
        this.folder = folder;
        this.path = path;
        this.uuid = uuid;
        this.fileName = fileName;
        this.type = type;
        this.size = size;
        this.createdAt = createdAt;
    }

    public static File of(User user, Folder folder, String path, String uuid, String fileName, String type, Long size, LocalDateTime createdAt) {
        return new File(user, folder, path, uuid, fileName, type, size, createdAt);
    }

    public static File from(File file, LocalDateTime createdAt) {
        return new File(file.getUser(), file.getFolder(), file.getPath(), file.getUuid(), file.getFileName(), file.getType(), file.getSize(), createdAt);
    }

    public void move(Folder folder) {
        this.folder = folder;
        updatePath();
    }

    public void updatePath() {
        if (this.folder == null) {
            this.path = "src/main/resources/upload/" + this.user.getEmail() + "/" + this.uuid + "." + this.type;
            return;
        }
        this.path = this.folder.getPath() + "/" + this.uuid + "." + this.type;
    }

}

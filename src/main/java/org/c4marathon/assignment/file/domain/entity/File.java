package org.c4marathon.assignment.file.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.global.aop.BaseTimeEntity;
import org.c4marathon.assignment.thumbnail.domain.entity.Thumbnail;
import org.c4marathon.assignment.user.domain.entity.User;

@Getter
@Entity
@Table(name = "MS_FILE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "folder_id")
    private Folder folder;

    @OneToOne(mappedBy = "file", fetch = FetchType.EAGER)
    private Thumbnail thumbnail;

    @Column(unique = true)
    private String path;

    private String uuid;

    private String fileName;

    private String type;

    private Long size;

    private File(User user, Folder folder, String path, String uuid, String fileName, String type, Long size) {
        this.id = null;
        this.user = user;
        this.folder = folder;
        this.path = path;
        this.uuid = uuid;
        this.fileName = fileName;
        this.type = type;
        this.size = size;
    }

    public static File of(User user, Folder folder, String path, String uuid, String fileName, String type, Long size) {
        return new File(user, folder, path, uuid, fileName, type, size);
    }

    public static File from(File file) {
        return new File(file.getUser(), file.getFolder(), file.getPath(), file.getUuid(), file.getFileName(), file.getType(), file.getSize());
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

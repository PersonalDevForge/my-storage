package org.c4marathon.assignment.folder.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.c4marathon.assignment.user.domain.entity.User;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "MS_FOLDER")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Folder parentFolder;

    @Length(max = 255)
    private String folderName;

    @Column(unique = true)
    @Length(max = 5000)
    private String path;

    private Long folderSize;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Folder(User user, Folder parentFolder, String folderName, String path, Long folderSize) {
        this.id = null;
        this.user = user;
        this.parentFolder = parentFolder;
        this.folderName = folderName;
        this.path = path;
        this.folderSize = folderSize;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public static Folder of(User user, Folder parentFolder, String folderName, String path, Long folderSize) {
        return new Folder(user, parentFolder, folderName, path, folderSize);
    }

    public void rename(String newFolderName, String newPath) {
        this.folderName = newFolderName;
        this.path = newPath;
    }

    public void updatePath(Folder parentFolder, String updatePath) {
        if (parentFolder == null) {
            this.path = updatePath + "/" + this.folderName;
            this.parentFolder = null;
            return;
        }
        this.parentFolder = parentFolder;
        this.path = updatePath + "/" + this.folderName;
    }

    public void renewUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

}

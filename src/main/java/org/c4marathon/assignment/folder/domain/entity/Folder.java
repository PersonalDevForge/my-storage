package org.c4marathon.assignment.folder.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.c4marathon.assignment.global.aop.BaseTimeEntity;
import org.c4marathon.assignment.user.domain.entity.User;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;


@Getter
@Entity
@Table(name = "MS_FOLDER")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Folder extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Folder parentFolder;

    @Length(max = 255)
    private String folderName;

    @Length(max = 3000)
    private String path;

    private Long innerFolderCount;

    private Long innerFileCount;

    private Long folderSize;

    private Folder(User user, Folder parentFolder, String folderName, String path, Long innerFolderCount, Long innerFileCount, Long folderSize) {
        this.id = null;
        this.user = user;
        this.parentFolder = parentFolder;
        this.folderName = folderName;
        this.path = path;
        this.innerFolderCount = innerFolderCount;
        this.innerFileCount = innerFileCount;
        this.folderSize = folderSize;
    }

    public static Folder of(User user, Folder parentFolder, String folderName, String path, Long innerFolderCount, Long innerFileCount, Long folderSize) {
        return new Folder(user, parentFolder, folderName, path, innerFolderCount, innerFileCount, folderSize);
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

    public void updateInnerFolderCount(Long count) {
        this.innerFolderCount = count;
    }

    public void updateInnerFileCount(Long count) {
        this.innerFileCount = count;
    }

    public void updateFolderSize(Long size) {
        this.folderSize = size;
    }

    public void addFolderSize(Long size) {
        this.folderSize += size;
    }

    public void initializeFolderSize() {
        this.folderSize = 0L;
    }

    public void renewUpdatedAt(LocalDateTime updatedAt) {
        super.renewUpdatedAt(updatedAt);
    }

}

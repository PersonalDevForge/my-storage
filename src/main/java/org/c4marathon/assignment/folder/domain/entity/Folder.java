package org.c4marathon.assignment.folder.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.c4marathon.assignment.user.domain.entity.User;

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

    private String folderName;

    private String path;

    private Folder(User user, Folder parentFolder, String folderName, String path) {
        this.id = null;
        this.user = user;
        this.parentFolder = parentFolder;
        this.folderName = folderName;
        this.path = path;
    }

    public static Folder of(User user, Folder parentFolder, String folderName, String path) {
        return new Folder(user, parentFolder, folderName, path);
    }

    public void rename(String newFolderName) {
        this.folderName = newFolderName;
    }

}

package org.c4marathon.assignment.folder.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "MS_FOLDER")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Folder parentFolder;

    private String folderName;

    private String path;

    private Folder(Folder parentFolder, String folderName, String path) {
        this.id = null;
        this.parentFolder = parentFolder;
        this.folderName = folderName;
        this.path = path;
    }

    public static Folder of(Folder parentFolder, String folderName, String path) {
        return new Folder(parentFolder, folderName, path);
    }

}

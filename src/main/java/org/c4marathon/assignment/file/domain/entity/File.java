package org.c4marathon.assignment.file.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "MS_FILE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String path;

    private String uuid;

    private String filename;

    private String type;

    private Long size;

    private LocalDateTime createdAt;

    private File(String path, String uuid, String filename, String type, Long size, LocalDateTime createdAt) {
        this.id = null;
        this.path = path;
        this.uuid = uuid;
        this.filename = filename;
        this.type = type;
        this.size = size;
        this.createdAt = createdAt;
    }

    public static File of(String path, String uuid, String filename, String type, Long size, LocalDateTime createdAt) {
        return new File(path, uuid, filename, type, size, createdAt);
    }

}

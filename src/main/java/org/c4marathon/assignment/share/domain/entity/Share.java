package org.c4marathon.assignment.share.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.global.aop.BaseTimeEntity;
import org.c4marathon.assignment.user.domain.entity.User;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "MS_SHARE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Share extends BaseTimeEntity {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    private Folder folder;

    private Boolean isFolder;

    private LocalDateTime expiredAt;
    
    public static Share of(String id, User user, File file, Folder folder, Boolean isFolder, LocalDateTime expiredAt) {
        return new Share(id, user, file, folder, isFolder, expiredAt);
    }

    public Boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiredAt);
    }

}

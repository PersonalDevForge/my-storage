package org.c4marathon.assignment.share.domain.entity;

import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.user.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.c4marathon.assignment.tools.TestTool.createFile;
import static org.c4marathon.assignment.tools.TestTool.createUser;
import static org.junit.jupiter.api.Assertions.*;

class ShareTest {

    @Test
    @DisplayName("isExpired()는 공유 링크가 만료됐다면 True를 반환한다.")
    void isExpiredReturnTrue() {
        // given
        User user = createUser();
        File file = createFile(user);
        Share share = createFileShare(user, file, LocalDateTime.now().minusDays(1));

        // when
        boolean isExpired = share.isExpired();

        // then
        assertTrue(isExpired);
    }

    @Test
    @DisplayName("isExpired()는 공유 링크가 만료되지 않았다면 False를 반환한다.")
    void isExpiredReturnFalse() {
        // given
        User user = createUser();
        File file = createFile(user);
        Share share = createFileShare(user, file, LocalDateTime.now().plusDays(1));

        // when
        boolean isExpired = share.isExpired();

        // then
        assertFalse(isExpired);
    }

    public static Share createFileShare(User user, File file, LocalDateTime expiredAt) {
        return Share.of("1", user, file, null, false, expiredAt);
    }

    public static Share createFolderShare(User user, Folder folder, LocalDateTime expiredAt) {
        return Share.of("1", user, null, folder, true, expiredAt);
    }

}
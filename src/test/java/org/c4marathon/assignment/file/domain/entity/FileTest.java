package org.c4marathon.assignment.file.domain.entity;

import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.user.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileTest {

    @Test
    @DisplayName("다른 Folder로 이동하면 그에 따라 Path가 업데이트 되어야 한다.")
    void move() {
        // given
        User user = User.of(1L, "test@example.com", "username", "password");
        String originFolderPath = "src/main/resources/upload/test@example.com/originFolderName";
        Folder originFolder = Folder.of(user, null, "originFolderNmae", originFolderPath, 0L, 0L, 0L);
        String destFolderPath = "src/main/resources/upload/test@example.com/destFolderName";
        Folder destFolder = Folder.of(user, null, "destFolderName", destFolderPath, 0L, 0L, 0L);
        File file = File.of(1L, user, originFolder, originFolderPath, "uuid", "fileName", "type", 1L);

        // when
        file.move(destFolder);

        // then
        assertEquals(destFolderPath + "/" + file.getUuid() + "." + file.getType(), file.getPath());
    }

    @Test
    @DisplayName("Root Folder로 이동하면 그에 따라 Path가 업데이트 되어야 한다.")
    void moveToRoot() {
        // given
        User user = User.of(1L, "test@example.com", "username", "password");
        String originFolderPath = "src/main/resources/upload/test@example.com/originFolderName";
        Folder originFolder = Folder.of(user, null, "originFolderNmae", originFolderPath, 0L, 0L, 0L);
        File file = File.of(1L, user, originFolder, originFolderPath, "uuid", "fileName", "type", 1L);
        String rootFolderPath = "src/main/resources/upload";

        // when
        file.move(null);

        // then
        assertEquals(rootFolderPath + "/" + file.getUser().getEmail() + "/" + file.getUuid() + "." + file.getType(), file.getPath());
    }

    @Test
    @DisplayName("현재 Folder가 null이면 Path는 src/main/resources/upload/{user.email}/{uuid}.{type}이어야 한다.")
    void updatePathWithNull() {
        // given
        User user = User.of(1L, "test@example.com", "username", "password");
        File file = File.of(1L, user, null, null, "uuid", "fileName", "type", 1L);
        String expectedPath = "src/main/resources/upload/" + user.getEmail() + "/" + file.getUuid() + "." + file.getType();

        // when
        file.updatePath();

        // then
        assertEquals(file.getPath(), expectedPath);
    }

    @Test
    @DisplayName("현재 Folder가 존재하면 Path는 {folder.path}/{uuid}.{type}이어야 한다.")
    void updatePathWithFolder() {
        // given
        User user = User.of(1L, "test@example.com", "username", "password");
        String folderPath = "src/main/resources/upload/test@example.com/originFolderName";
        Folder folder = Folder.of(user, null, "originFolderNmae", folderPath, 0L, 0L, 0L);
        File file = File.of(1L, user, folder, null, "uuid", "fileName", "type", 1L);
        String expectedPath = folderPath + "/" + file.getUuid() + "." + file.getType();

        // when
        file.updatePath();

        // then
        assertEquals(file.getPath(), expectedPath);
    }

}
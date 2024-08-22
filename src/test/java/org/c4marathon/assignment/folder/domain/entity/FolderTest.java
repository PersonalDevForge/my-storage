package org.c4marathon.assignment.folder.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FolderTest {

    @Test
    @DisplayName("폴더의 이름과 경로를 변경할 수 있다.")
    void rename() {
        // given
        Folder folder = Folder.of(1L, null, null, "oldFolderName", "oldPath", 0L, 0L, 0L);
        String newFolderName = "newFolderName";
        String newPath = "newPath";

        // when
        folder.rename(newFolderName, newPath);

        // then
        assertThat(folder.getFolderName()).isEqualTo(newFolderName);
        assertThat(folder.getPath()).isEqualTo(newPath);
    }

    @Test
    @DisplayName("부모 폴더로 폴더를 옮기고, 폴더의 path를 업데이트할 수 있다.")
    void updatePathToParentFolder() {
        // given
        Folder oldFolder = Folder.of(1L, null, null, "oldFolderName", "oldPath", 0L, 0L, 0L);
        Folder parentFolder = Folder.of(1L, null, null, "parentFolder", "parentFolderPath", 0L, 0L, 0L);
        String parentPath = "update/parent/folder/path";

        // when
        oldFolder.updatePath(parentFolder, parentPath);

        // then
        assertThat(oldFolder.getParentFolder()).isEqualTo(parentFolder);
        assertThat(oldFolder.getPath()).isEqualTo(parentPath + "/" + oldFolder.getFolderName());
    }

    @Test
    @DisplayName("부모 폴더로 폴더를 옮기고, 폴더의 path를 업데이트할 수 있다. (부모 폴더가 Root 폴더인 경우)")
    void updatePathToRootParentFolder() {
        // given
        Folder oldFolder = Folder.of(1L, null, null, "oldFolderName", "oldPath", 0L, 0L, 0L);
        Folder parentFolder = null;
        String parentPath = "update/parent/folder/path";

        // when
        oldFolder.updatePath(parentFolder, parentPath);

        // then
        assertThat(oldFolder.getParentFolder()).isEqualTo(parentFolder);
        assertThat(oldFolder.getPath()).isEqualTo(parentPath + "/" + oldFolder.getFolderName());
    }

    @Test
    @DisplayName("count만큼 폴더의 innerFolderCount를 업데이트할 수 있다.")
    void updateInnerFolderCount() {
        // given
        Folder folder = Folder.of(1L, null, null, "oldFolderName", "oldPath", 0L, 0L, 0L);
        Long count = 10L;

        // when
        folder.updateInnerFolderCount(count);

        // then
        assertThat(folder.getInnerFolderCount()).isEqualTo(count);
    }

    @Test
    @DisplayName("count만큼 폴더의 innerFileCount를 업데이트할 수 있다.")
    void updateInnerFileCount() {
        // given
        Folder folder = Folder.of(1L, null, null, "oldFolderName", "oldPath", 0L, 0L, 0L);
        Long count = 10L;

        // when
        folder.updateInnerFileCount(count);

        // then
        assertThat(folder.getInnerFileCount()).isEqualTo(count);
    }

    @Test
    @DisplayName("size만큼 폴더의 folderSize를 업데이트할 수 있다.")
    void updateFolderSize() {
        // given
        Folder folder = Folder.of(1L, null, null, "oldFolderName", "oldPath", 0L, 0L, 10L);
        Long size = 10L;

        // when
        folder.updateFolderSize(size);

        // then
        assertThat(folder.getFolderSize()).isEqualTo(size);
    }

    @Test
    @DisplayName("size만큼 폴더의 folderSize를 더할 수 있다.")
    void addFolderSize() {
        // given
        Folder folder = Folder.of(1L, null, null, "oldFolderName", "oldPath", 0L, 0L, 10L);
        Long size = 10L;

        // when
        folder.addFolderSize(size);

        // then
        assertThat(folder.getFolderSize()).isEqualTo(20L);
    }

    @Test
    @DisplayName("폴더의 folderSize를 0으로 초기화할 수 있다.")
    void initializeFolderSize() {
        // given
        Folder folder = Folder.of(1L, null, null, "oldFolderName", "oldPath", 0L, 0L, 10L);

        // when
        folder.initializeFolderSize();

        // then
        assertThat(folder.getFolderSize()).isEqualTo(0L);
    }

}
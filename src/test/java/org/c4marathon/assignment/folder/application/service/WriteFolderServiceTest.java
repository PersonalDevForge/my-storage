package org.c4marathon.assignment.folder.application.service;

import org.c4marathon.assignment.file.application.service.component.FileFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class WriteFolderServiceTest {

    @Mock
    private FileFactory fileFactory;

    @InjectMocks
    private WriteFolderService writeFolderService;

    @Test
    @DisplayName("sub directory를 포함한 폴더 계층을 생성할 수 있다. (부모 디렉토리가 루트 디렉토리인 경우)")
    void writeFolderWithSubDirectoryForRootParentDirectory() {
        // given
        String email = "test@example.com";
        String folderName = "folderName";
        String parentFolderPath = null;

        // mock
        File file = mock(File.class);
        when(fileFactory.createFile("src/main/resources/upload/" + email + "/" + folderName)).thenReturn(file);
        when(file.exists()).thenReturn(false);
        when(file.mkdirs()).thenReturn(true);
        when(file.getPath()).thenReturn("src/main/resources/upload/" + email + "/" + folderName);

        // when
        String result = writeFolderService.writeFolder(email, folderName, parentFolderPath);

        // then
        assertEquals("src/main/resources/upload/" + email + "/" + folderName, result);
    }

    @Test
    @DisplayName("sub directory를 포함한 폴더 계층을 생성할 수 있다. (부모 디렉토리가 루트 디렉토리가 아닌 경우)")
    void writeFolderWithSubDirectory() {
        // given
        String email = "test@example.com";
        String folderName = "folderName";
        String parentFolderPath = "src/main/resources/upload/" + email + "/parentFolder";

        // mock
        File file = mock(File.class);
        when(fileFactory.createFile(parentFolderPath + "/" + folderName)).thenReturn(file);
        when(file.exists()).thenReturn(false);
        when(file.mkdirs()).thenReturn(true);
        when(file.getPath()).thenReturn(parentFolderPath + "/" + folderName);

        // when
        String result = writeFolderService.writeFolder(email, folderName, parentFolderPath);

        // then
        assertEquals(parentFolderPath + "/" + folderName, result);
    }

    @Test
    @DisplayName("폴더가 이미 존재한다면 IllegalArgumentException을 던진다.")
    void failWhenFolderAlreadyExist() {
        // given
        String email = "test@example.com";
        String folderName = "folderName";
        String parentFolderPath = "src/main/resources/upload/" + email + "/parentFolder";

        // mock
        File file = mock(File.class);
        when(fileFactory.createFile(parentFolderPath + "/" + folderName)).thenReturn(file);
        when(file.exists()).thenReturn(true);
        when(file.mkdirs()).thenReturn(true);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> writeFolderService.writeFolder(email, folderName, parentFolderPath));
    }

    @Test
    @DisplayName("폴더 생성에 실패한다면 IllegalArgumentException을 던진다.")
    void failToMkdirs() {
        // given
        String email = "test@example.com";
        String folderName = "folderName";
        String parentFolderPath = "src/main/resources/upload/" + email + "/parentFolder";

        // mock
        File file = mock(File.class);
        when(fileFactory.createFile(parentFolderPath + "/" + folderName)).thenReturn(file);
        when(file.exists()).thenReturn(false);
        when(file.mkdirs()).thenReturn(false);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> writeFolderService.writeFolder(email, folderName, parentFolderPath));
    }

}
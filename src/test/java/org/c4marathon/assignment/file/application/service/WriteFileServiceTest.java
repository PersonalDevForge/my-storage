package org.c4marathon.assignment.file.application.service;

import org.c4marathon.assignment.file.application.service.component.FileOutputStreamFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class WriteFileServiceTest {

    @Mock
    private FileOutputStreamFactory fileOutputStreamFactory;

    @InjectMocks
    private WriteFileService writeFileService;

    @Test
    @DisplayName("type이 null이 아닌 경우 .{type}을 붙인 파일 시스템상의 경로를 만들 수 있다.")
    void makeDestinationPathWithTypeNotNull() throws IOException {
        // given
        String folderPath = "src/main/resources/upload/test@example.com";
        String uuidFileName = "uuidFileName";
        String type = "type";
        String expectedPath = "src/main/resources/upload/test@example.com/uuidFileName.type";

        // when
        String result = writeFileService.makeDestinationPath(folderPath, uuidFileName, type);

        // then
        assertEquals(expectedPath, result);
    }

    @Test
    @DisplayName("type이 null인 경우 .{type}을 붙이지 않은 파일 시스템상의 경로를 만들 수 있다.")
    void makeDestinationPathWithTypeNull() throws IOException {
        // given
        String folderPath = "src/main/resources/upload/test@example.com";
        String uuidFileName = "uuidFileName";
        String type = null;
        String expectedPath = "src/main/resources/upload/test@example.com/uuidFileName";

        // when
        String result = writeFileService.makeDestinationPath(folderPath, uuidFileName, type);

        // then
        assertEquals(expectedPath, result);
    }

    @Test
    @DisplayName("파일 시스템상의 경로 문자열을 이용하여 File 객체를 생성할 수 있다.")
    void pathToFile() throws IOException {
        // given
        String destinationPath = "src/main/resources/upload/test@example.com/uuidFileName.type";

        // when
        File result = writeFileService.pathToFile(destinationPath);

        // then
        assertEquals(destinationPath, result.toString());
    }


    @Test
    @DisplayName("파일을 파일 시스템에 저장하면 파일 시스템상의 파일 경로를 반환한다.")
    void writeFileThenReturnFileSystemPath() throws IOException {
        // given
        byte[] file = new byte[0];
        FileOutputStream mockedFileOutputStream = mock(FileOutputStream.class);
        File mockedUploadServerDir = mock(File.class);
        File mockedUploadServerFile = mock(File.class);
        when(mockedUploadServerDir.exists()).thenReturn(true);
        when(mockedUploadServerFile.exists()).thenReturn(false);
        when(mockedUploadServerFile.createNewFile()).thenReturn(true);
        when(fileOutputStreamFactory.createFileOutputStream(mockedUploadServerFile)).thenReturn(mockedFileOutputStream);

        // when
        String result = writeFileService.writeFile(mockedUploadServerDir, mockedUploadServerFile, file);

        // then
        assertEquals(mockedUploadServerFile.toString(), result);
    }

    @Test
    @DisplayName("이미 파일 시스템상에 파일이 존재하면 RuntimeException을 던진다.")
    void writeFileAlreadyFileExist() throws IOException {
        // given
        byte[] file = new byte[0];
        FileOutputStream mockedFileOutputStream = mock(FileOutputStream.class);
        File mockedUploadServerDir = mock(File.class);
        File mockedUploadServerFile = mock(File.class);
        when(mockedUploadServerDir.exists()).thenReturn(true);
        when(mockedUploadServerFile.exists()).thenReturn(true);
        when(mockedUploadServerFile.createNewFile()).thenReturn(true);
        when(fileOutputStreamFactory.createFileOutputStream(mockedUploadServerFile)).thenReturn(mockedFileOutputStream);

        // when & then
        assertThrows(RuntimeException.class, () -> writeFileService.writeFile(mockedUploadServerDir, mockedUploadServerFile, file));
    }

    @Test
    @DisplayName("파일 시스템상에 디렉토리를 생성하는 것에 실패하면 RuntimeException을 던진다.")
    void writeFileFailToMakeDirectories() throws IOException {
        // given
        byte[] file = new byte[0];
        FileOutputStream mockedFileOutputStream = mock(FileOutputStream.class);
        File mockedUploadServerDir = mock(File.class);
        File mockedUploadServerFile = mock(File.class);
        when(mockedUploadServerDir.exists()).thenReturn(false);
        when(mockedUploadServerDir.mkdirs()).thenReturn(false);
        when(mockedUploadServerFile.exists()).thenReturn(false);
        when(mockedUploadServerFile.createNewFile()).thenReturn(true);
        when(fileOutputStreamFactory.createFileOutputStream(mockedUploadServerFile)).thenReturn(mockedFileOutputStream);

        // when & then
        assertThrows(RuntimeException.class, () -> writeFileService.writeFile(mockedUploadServerDir, mockedUploadServerFile, file));
    }

    @Test
    @DisplayName("파일 시스템상에 파일을 생성하는 것에 실패하면 RuntimeException을 던진다.")
    void writeFileFailToCreateNewFile() throws IOException {
        // given
        byte[] file = new byte[0];
        FileOutputStream mockedFileOutputStream = mock(FileOutputStream.class);
        File mockedUploadServerDir = mock(File.class);
        File mockedUploadServerFile = mock(File.class);
        when(mockedUploadServerDir.exists()).thenReturn(true);
        when(mockedUploadServerFile.exists()).thenReturn(false);
        when(mockedUploadServerFile.createNewFile()).thenReturn(false);
        when(fileOutputStreamFactory.createFileOutputStream(mockedUploadServerFile)).thenReturn(mockedFileOutputStream);

        // when & then
        assertThrows(RuntimeException.class, () -> writeFileService.writeFile(mockedUploadServerDir, mockedUploadServerFile, file));
    }

    @Test
    @DisplayName("파일 시스템상의 파일에 데이터를 쓰는 것에 실패하면 RuntimeException을 던진다.")
    void writeFileFailWriteDataToFileSystem() throws IOException {
        // given
        byte[] file = new byte[0];
        FileOutputStream mockedFileOutputStream = mock(FileOutputStream.class);
        File mockedUploadServerDir = mock(File.class);
        File mockedUploadServerFile = mock(File.class);
        when(mockedUploadServerDir.exists()).thenReturn(true);
        when(mockedUploadServerFile.exists()).thenReturn(false);
        when(mockedUploadServerFile.createNewFile()).thenReturn(true);
        doThrow(new IOException()).when(mockedFileOutputStream).write(file);
        when(fileOutputStreamFactory.createFileOutputStream(mockedUploadServerFile)).thenReturn(mockedFileOutputStream);

        // when & then
        assertThrows(RuntimeException.class, () -> writeFileService.writeFile(mockedUploadServerDir, mockedUploadServerFile, file));
    }

}
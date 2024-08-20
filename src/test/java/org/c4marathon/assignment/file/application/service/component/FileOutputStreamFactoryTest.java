package org.c4marathon.assignment.file.application.service.component;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class FileOutputStreamFactoryTest {

    private final String validTestFilePath = "src/test/resources/testFile";

    @AfterEach
    void tearDown() {
        File file = new File(validTestFilePath);
        file.delete();
    }

    @Test
    @DisplayName("존재하는 경로의 File을 이용하여 FileOutputStream를 생성할 수 있다.")
    void createFileOutputStream() {
        // given
        File file = new File(validTestFilePath);

        // when
        FileOutputStream result = new FileOutputStreamFactory().createFileOutputStream(file);

        // then
        assertNotNull(result);
    }

    @Test
    @DisplayName("FileOutputStream 생성에 실패하면 RuntimeException을 던진다.")
    void createFileOutputStreamFail() {
        // given
        String invalidTestFilePath = "src/test/resources/invalid/file/path/testFile";
        File file = new File(invalidTestFilePath);

        // when & then
        assertThrows(RuntimeException.class, () -> new FileOutputStreamFactory().createFileOutputStream(file));
    }

}
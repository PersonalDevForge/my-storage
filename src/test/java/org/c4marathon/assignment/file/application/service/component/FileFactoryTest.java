package org.c4marathon.assignment.file.application.service.component;

import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileFactoryTest {

    private final FileFactory fileFactory = new FileFactory();

    private static MockedStatic<Files> files;

    @BeforeAll
    public static void beforeAll() {
        files = mockStatic(Files.class);
    }

    @AfterAll
    public static void afterAll() {
        files.close();
    }

    @Test
    @DisplayName("createPath() 메서드는 주어진 경로를 Path 객체로 변환한다.")
    void createPath() {
        // given
        String path = "src/test/resources/test.txt";

        // when
        Path result = fileFactory.createPath(path);

        // then
        assertEquals(result.toString(), path);
    }

    @Test
    @DisplayName("createFile() 메서드는 주어진 경로를 File 객체로 변환한다.")
    void createFile() {
        // given
        String path = "src/test/resources/test.txt";

        // when
        File result = fileFactory.createFile(path);

        // then
        assertEquals(result.toString(), path);
    }

    @Test
    @DisplayName("copyFile() 메서드는 파일을 복사한다.")
    void copyFile() throws IOException {
        // given
        Path origin = Path.of("src/test/resources/test.txt");
        Path target = Path.of("src/test/resources/test - Copy.txt");

        // mock
        when(Files.copy(origin, target)).thenReturn(target);

        // when
        Path result = fileFactory.copyFile(origin, target);

        // then
        assertEquals(result.toString(), target.toString());
    }

    @Test
    @DisplayName("deleteFile() 메서드는 파일을 삭제한다.")
    void deleteFile() throws IOException {
        // given
        Path path = Path.of("src/test/resources/test.txt");

        // when & then
        assertDoesNotThrow(() -> fileFactory.deleteFile(path));
    }


}
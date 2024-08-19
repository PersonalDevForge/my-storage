package org.c4marathon.assignment.file.application.service.component;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileFactoryTest {

    private final FileFactory fileFactory = new FileFactory();

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

}
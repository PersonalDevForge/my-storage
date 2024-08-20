package org.c4marathon.assignment.file.application.service.component;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@NoArgsConstructor
public class FileFactory {

    public Path createPath(String path) {
        return Path.of(path);
    }

    public File createFile(String path) {
        return new File(path);
    }

    public Path copyFile(Path origin, Path target) throws IOException {
        return Files.copy(origin, target);
    }

}

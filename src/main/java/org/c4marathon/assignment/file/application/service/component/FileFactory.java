package org.c4marathon.assignment.file.application.service.component;

import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;

@Component
public class FileFactory {

    public Path createPath(String path) {
        return Path.of(path);
    }

    public File createFile(String path) {
        return new File(path);
    }

}

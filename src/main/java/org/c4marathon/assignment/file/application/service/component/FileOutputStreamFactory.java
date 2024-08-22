package org.c4marathon.assignment.file.application.service.component;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Component
@NoArgsConstructor
public class FileOutputStreamFactory {

    public FileOutputStream createFileOutputStream(File uploadServerFile) {
        try {
            return new FileOutputStream(uploadServerFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found", e);
        }
    }

}

package org.c4marathon.assignment.file.application.service;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.in.WriteToFileSystemUseCase;
import org.c4marathon.assignment.file.application.service.component.FileOutputStreamFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class WriteFileService implements WriteToFileSystemUseCase {

    private final FileOutputStreamFactory fileOutputStreamFactory;

    @Override
    public String makeDestinationPath(String folderPath, String uuidFileName, String type) {
        return (type == null || type.isEmpty()) ? (folderPath + "/" + uuidFileName) : (folderPath + "/" + uuidFileName + "." + type);
    }

    @Override
    public File pathToFile(String path) {
        Path fileSystemPath = Path.of(path);
        return fileSystemPath.toFile();
    }

    private void verifyFileExist(File uploadServerFile) {
        if (uploadServerFile.exists()) {
            throw new RuntimeException("File already exists");
        }
    }

    private void makeDirectories(File uploadServerDir) {
        if (!uploadServerDir.exists()) {
            if (!uploadServerDir.mkdirs()) {
                throw new RuntimeException("Failed to create directory");
            }
        }
    }

    @Override
    public String writeFile(File uploadServerDir, File uploadServerFile, byte[] file) {
        // 파일이 존재한다면 실패한다.
        verifyFileExist(uploadServerFile);

        // 폴더가 존재하지 않는다면 생성한다.
        makeDirectories(uploadServerDir);

        try {
            // 파일을 생성한다.
            if (!uploadServerFile.createNewFile()) {
                throw new RuntimeException("Failed to create file");
            }
            // 파일에 데이터를 쓴다.
            FileOutputStream fileOutputStream = fileOutputStreamFactory.createFileOutputStream(uploadServerFile);
            fileOutputStream.write(file);
            fileOutputStream.close();
            return uploadServerFile.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write file");
        }
    }

}

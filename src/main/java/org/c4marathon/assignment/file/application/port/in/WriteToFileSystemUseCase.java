package org.c4marathon.assignment.file.application.port.in;

import java.io.File;

public interface WriteToFileSystemUseCase {

    String makeDestinationPath(String folderPath, String uuidFileName, String type);

    File pathToFile(String path);

    String writeFile(File uploadServerDir, File uploadServerFile, byte[] file);

}

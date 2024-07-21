package org.c4marathon.assignment.file.application.port.in;

import org.c4marathon.assignment.file.domain.entity.File;

import java.util.List;

public interface GetFileListUseCase {

    List<File> getFileList(String email);

    File getFile(String email, String fileName);

}

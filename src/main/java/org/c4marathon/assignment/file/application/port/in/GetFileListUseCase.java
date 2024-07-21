package org.c4marathon.assignment.file.application.port.in;

import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.user.domain.entity.User;

import java.util.List;

public interface GetFileListUseCase {

    List<File> getFileList(User user);

    File getFile(User user, String fileName);

    List<File> getFileListByFolder(User user, Long folderId);

}

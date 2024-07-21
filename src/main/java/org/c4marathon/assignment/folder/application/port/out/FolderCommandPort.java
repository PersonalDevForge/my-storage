package org.c4marathon.assignment.folder.application.port.out;

import org.c4marathon.assignment.folder.domain.entity.Folder;

public interface FolderCommandPort {

    Folder save(Folder folder);

    Folder update(Folder folder);

    void delete(Folder folder);

}

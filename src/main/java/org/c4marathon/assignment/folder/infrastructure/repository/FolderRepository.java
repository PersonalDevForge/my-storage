package org.c4marathon.assignment.folder.infrastructure.repository;

import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {

    List<Folder> findByParentFolder(Folder parentFolder);

    List<Folder> findByPath(String path);

}

package org.c4marathon.assignment.file.adapter.out;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.out.FileQueryPort;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.file.infrastructure.repository.FileRepository;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FileQueryAdapter implements FileQueryPort {

    private final FileRepository fileRepository;

    private final EntityManager entityManager;

    @Override
    public List<File> findAllByUser(User user) {
        return fileRepository.findAllByUser(user);
    }

    @Override
    public Optional<File> findByUserAndId(User user, Long id) {
        return fileRepository.findByUserAndId(user, id);
    }

    @Override
    public Optional<File> findByUserAndFileNameAndFolder(User user, String fileName, Folder folder) {
        return fileRepository.findByUserAndFileNameAndFolder(user, fileName, folder);
    }

    @Override
    public Optional<File> findByPath(String path) {
        return fileRepository.findByPath(path);
    }

    @Override
    public List<File> findAllByUserAndFolder(User user, Folder folder) {
        return fileRepository.findAllByUserAndFolder(user, folder);
    }

    @Override
    public Page<File> findAllByUserAndFolderPageable(User user, Long folderId, Pageable pageable) {
        return fileRepository.findAllByUserAndFolderPageable(user, folderId, pageable);
    }

    @Override
    public List<File> findFilesWithOffset(User user, Long folderId, int offset, int limit) {
        String jpql = "SELECT f FROM File f WHERE f.user = :user AND"
                + " (:folderId IS NULL AND f.folder IS NULL OR f.folder.id = :folderId)"
                + " ORDER BY f.id ASC";

        TypedQuery<File> query = entityManager.createQuery(jpql, File.class);
        query.setParameter("user", user);
        query.setParameter("folderId", folderId);

        // 설정한 offset과 limit 적용
        query.setFirstResult(offset);  // offset: 스킵할 데이터 수
        query.setMaxResults(limit);    // limit: 가져올 데이터 수

        // 결과 반환
        return query.getResultList();
    }

}

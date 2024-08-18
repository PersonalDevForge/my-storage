package org.c4marathon.assignment.thumbnail.infrastructure.repository;

import org.c4marathon.assignment.thumbnail.domain.entity.Thumbnail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ThumbnailRepository extends JpaRepository<Thumbnail, Long> {

    @Query("SELECT t FROM Thumbnail t WHERE t.id = :id")
    Optional<Thumbnail> findById(Long id);

    @Query("SELECT t FROM Thumbnail t WHERE t.file.id = :fileId")
    Optional<Thumbnail> findByFileId(Long fileId);

    @Query("SELECT t FROM Thumbnail t WHERE t.folder.id = :folderId")
    List<Thumbnail> findAllByFolderId(Long folderId);

}

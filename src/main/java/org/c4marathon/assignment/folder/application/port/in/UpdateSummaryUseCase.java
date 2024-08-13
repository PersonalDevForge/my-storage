package org.c4marathon.assignment.folder.application.port.in;

import org.c4marathon.assignment.user.domain.entity.User;

import java.time.LocalDateTime;

public interface UpdateSummaryUseCase {

    void updateSummary(User user, Long folderId, LocalDateTime updatedAt);

}

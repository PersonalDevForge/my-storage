package org.c4marathon.assignment.folder.application.port.in;

import org.c4marathon.assignment.folder.adapter.in.dto.FolderSummaryResponseDto;
import org.c4marathon.assignment.user.domain.entity.User;

public interface GetFolderSummaryUseCase {
    FolderSummaryResponseDto getFolderSummary(User user, String folderId);
}

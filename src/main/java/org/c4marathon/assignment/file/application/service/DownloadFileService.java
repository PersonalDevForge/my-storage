package org.c4marathon.assignment.file.application.service;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.in.DownloadFileUseCase;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.user.application.service.UserSearchService;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DownloadFileService implements DownloadFileUseCase {

    private final UserSearchService userSearchService;

    private final FileSearchService fileSearchService;

    @Override
    public String downloadFile(String email, String filename) {
        User user = userSearchService.getUserByEmail(email);
        File file = fileSearchService.getFile(email, filename.substring(0, filename.indexOf('.')));
        return file.getPath();
    }
}

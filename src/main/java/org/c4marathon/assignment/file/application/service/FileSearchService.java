package org.c4marathon.assignment.file.application.service;

import lombok.RequiredArgsConstructor;
import org.c4marathon.assignment.file.application.port.in.GetFileListUseCase;
import org.c4marathon.assignment.file.application.port.out.FileQueryPort;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.user.application.service.UserSearchService;
import org.c4marathon.assignment.user.domain.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileSearchService implements GetFileListUseCase {

    private final FileQueryPort fileQueryPort;

    private final UserSearchService userSearchService;

    @Override
    public List<File> getFileList(String email) {
        User user = userSearchService.getUserByEmail(email);
        return fileQueryPort.findAllByUser(user);
    }

}

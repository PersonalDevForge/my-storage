package org.c4marathon.assignment.file.application.service;

import org.c4marathon.assignment.file.application.port.out.FileQueryPort;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.application.port.out.FolderQueryPort;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.global.exception.customs.NotFoundException;
import org.c4marathon.assignment.user.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class FileSearchServiceTest {

    @Mock
    private FileQueryPort fileQueryPort;

    @Mock
    private FolderQueryPort folderQueryPort;

    @InjectMocks
    private FileSearchService fileSearchService;

    @Test
    @DisplayName("User의 모든 파일을 조회할 수 있다.")
    void getFileList() {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        File file1 = File.of(1L, user, null, "path", "uuid", "filename1", "type", 10L);
        File file2 = File.of(2L, user, null, "path", "uuid", "filename2", "type", 10L);
        List fileList = List.of(file1, file2);
        when(fileQueryPort.findAllByUser(user)).thenReturn(fileList);

        // when
        List<File> result = fileSearchService.getFileList(user);

        // then
        assertEquals(fileList, result);
    }

    @Test
    @DisplayName("존재하는 파일에 대해서 유저의 특정 파일 하나를 조회할 수 있다.")
    void getFileExist() {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        Long fileId = 1L;
        File file = File.of(fileId, user, null, "path", "uuid", "filename1", "type", 10L);
        when(fileQueryPort.findByUserAndId(user, fileId)).thenReturn(Optional.of(file));

        // when
        File result = fileSearchService.getFile(user, fileId);

        // then
        assertEquals(file, result);
    }

    @Test
    @DisplayName("존재하지 않는 파일에 대해서 유저의 특정 파일 하나를 조회할 수 없다.")
    void getFileFailNotExist() {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        Long fileId = 1L;
        when(fileQueryPort.findByUserAndId(user, fileId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> fileSearchService.getFile(user, fileId));
    }

    @Test
    @DisplayName("유저의 특정 폴더에 속한 파일 목록을 조회할 수 있다.")
    void getFileListByFolder() {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        File file1 = File.of(1L, user, null, "path", "uuid", "filename1", "type", 10L);
        File file2 = File.of(2L, user, null, "path", "uuid", "filename2", "type", 10L);
        List fileList = List.of(file1, file2);
        Long rootFolderId = null;
        Folder rootFolder = null;
        when(fileQueryPort.findAllByUserAndFolder(user, rootFolder)).thenReturn(fileList);

        // when
        List<File> result = fileSearchService.getFileListByFolder(user, rootFolderId);

        // then
        assertEquals(fileList, result);
    }

    @Test
    @DisplayName("유저의 특정 폴더에 속한 파일 목록을 offset과 limit를 이용하여 페이징하여 조회할 수 있다.")
    void getFileListByFolderPageable() {
        // given
        User user = User.of(1L, "test@example.com", "nickname", "password");
        File file1 = File.of(1L, user, null, "path", "uuid", "filename1", "type", 10L);
        List fileList = List.of(file1);
        Long rootFolderId = null;
        int offset = 0;
        int limit = 1;
        when(fileQueryPort.findFilesWithOffset(user, rootFolderId, offset, limit)).thenReturn(fileList);

        // when
        List<File> result = fileSearchService.getFileListByFolderPageable(user, rootFolderId, offset, limit);

        // then
        assertEquals(fileList, result);
    }
}
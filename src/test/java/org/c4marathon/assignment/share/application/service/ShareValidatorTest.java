package org.c4marathon.assignment.share.application.service;

import org.c4marathon.assignment.file.application.service.FileSearchService;
import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.folder.domain.entity.Folder;
import org.c4marathon.assignment.global.exception.customs.ExpiredLinkException;
import org.c4marathon.assignment.share.application.port.out.ShareCommandPort;
import org.c4marathon.assignment.share.domain.entity.Share;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ShareValidatorTest {

    @Mock
    private ShareCommandPort shareCommandPort;

    @Mock
    private FileSearchService fileSearchService;

    @InjectMocks
    private ShareValidator shareValidator;

    @Test
    @DisplayName("Share가 폴더에 대한 공유가 아니라면 IllegalArgumentException을 던진다.")
    void validateIsFolder() {
        // given
        Share share = mock(Share.class);
        when(share.getIsFolder()).thenReturn(false);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> shareValidator.validateFolderShare(share));
    }

    @Test
    @DisplayName("Share가 폴더에 대한 공유이지만 만료되었다면 ExpiredLinkException을 던지고 Share를 삭제한다.")
    void validateIsFolderButExpired() {
        // given
        Share share = mock(Share.class);
        when(share.getIsFolder()).thenReturn(true);
        when(share.isExpired()).thenReturn(true);

        // when & then
        assertThrows(ExpiredLinkException.class, () -> shareValidator.validateFolderShare(share));
        verify(shareCommandPort, times(1)).delete(share);
    }

    @Test
    @DisplayName("Share가 폴더에 대한 공유이고 만료되지 않았다면 아무런 예외를 던지지 않는다.")
    void validateIsFolderAndNotExpired() {
        // given
        Share share = mock(Share.class);
        when(share.getIsFolder()).thenReturn(true);
        when(share.isExpired()).thenReturn(false);

        // when & then
        assertDoesNotThrow(() -> shareValidator.validateFolderShare(share));
    }

    @Test
    @DisplayName("파일이 공유받은 폴더에 속하지 않는다면 IllegalArgumentException을 던진다.")
    void validateIsInSharedFolder() {
        // given
        Long fileId = 1L;
        Share share = mock(Share.class);
        File file = mock(File.class);
        Folder folder1 = mock(Folder.class);
        Folder folder2 = mock(Folder.class);
        when(fileSearchService.getFile(share.getUser(), fileId)).thenReturn(file);
        when(share.getFolder()).thenReturn(folder1);
        when(folder1.getId()).thenReturn(1L);
        when(file.getFolder()).thenReturn(folder2);
        when(folder2.getId()).thenReturn(2L);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> shareValidator.validateIsInSharedFolder(share, fileId));
    }

    @Test
    @DisplayName("파일이 공유받은 폴더에 속하지 않는다면 IllegalArgumentException을 던진다. (공유 폴더가 루트 폴더인 경우)")
    void validateIsInSharedFolderCaseRootFolderButFileIsNotInRoot() {
        // given
        Long fileId = 1L;
        Share share = mock(Share.class);
        File file = mock(File.class);
        Folder folder = mock(Folder.class);
        when(fileSearchService.getFile(share.getUser(), fileId)).thenReturn(file);
        when(share.getFolder()).thenReturn(null);
        when(file.getFolder()).thenReturn(folder);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> shareValidator.validateIsInSharedFolder(share, fileId));
    }

    @Test
    @DisplayName("파일이 공유받은 폴더에 속하지 않는다면 IllegalArgumentException을 던진다. (공유 폴더가 루트 폴더가 아니고 파일이 루트 폴더에 있는 경우)")
    void validateIsInSharedFolderCaseRootFolderButShareFolderIsNotRoot() {
        // given
        Long fileId = 1L;
        Share share = mock(Share.class);
        File file = mock(File.class);
        Folder folder = mock(Folder.class);
        when(fileSearchService.getFile(share.getUser(), fileId)).thenReturn(file);
        when(share.getFolder()).thenReturn(folder);
        when(file.getFolder()).thenReturn(null);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> shareValidator.validateIsInSharedFolder(share, fileId));
    }


    @Test
    @DisplayName("파일이 공유받은 폴더에 속한다면 아무런 예외를 던지지 않는다.")
    void validateIsInSharedFolderValid() {
        // given
        Long fileId = 1L;
        Share share = mock(Share.class);
        File file = mock(File.class);
        Folder folder1 = mock(Folder.class);
        Folder folder2 = mock(Folder.class);
        when(fileSearchService.getFile(share.getUser(), fileId)).thenReturn(file);
        when(share.getFolder()).thenReturn(folder1);
        when(folder1.getId()).thenReturn(1L);
        when(file.getFolder()).thenReturn(folder2);
        when(folder2.getId()).thenReturn(1L);

        // when & then
        assertDoesNotThrow(() -> shareValidator.validateIsInSharedFolder(share, fileId));
    }
}
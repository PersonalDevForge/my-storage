package org.c4marathon.assignment.share.application.service;

import org.c4marathon.assignment.file.application.port.in.DeleteFileUseCase;
import org.c4marathon.assignment.share.application.port.in.GetShareUseCase;
import org.c4marathon.assignment.share.domain.entity.Share;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class DeleteFileToSharedFolderServiceTest {

    @Mock
    private GetShareUseCase getShareUseCase;

    @Mock
    private ShareValidator shareValidator;

    @Mock
    private DeleteFileUseCase deleteFileUseCase;

    @InjectMocks
    private DeleteFileToSharedFolderService deleteFileToSharedFolderService;

    @Test
    @DisplayName("공유받은 폴더 내부의 파일을 누구나 삭제할 수 있다.")
    void deleteFileToSharedFolder() {
        // given
        String uuid = "uuid";
        Long fileId = 1L;
        Share share = mock(Share.class);
        when(getShareUseCase.getShare(uuid)).thenReturn(share);

        // when & then
        assertDoesNotThrow(() -> deleteFileToSharedFolderService.deleteFileToSharedFolder(uuid, fileId));
    }

}
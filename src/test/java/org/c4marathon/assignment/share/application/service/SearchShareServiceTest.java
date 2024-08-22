package org.c4marathon.assignment.share.application.service;

import org.c4marathon.assignment.global.exception.customs.NotFoundException;
import org.c4marathon.assignment.share.application.port.out.ShareQueryPort;
import org.c4marathon.assignment.share.domain.entity.Share;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class SearchShareServiceTest {

    @Mock
    private ShareQueryPort shareQueryPort;

    @InjectMocks
    private SearchShareService searchShareService;

    @Test
    @DisplayName("uuid를 이용하여 Share를 찾을 수 있다.")
    void getShareByUUID() {
        // given
        Share share = mock(Share.class);
        when(shareQueryPort.findById(anyString())).thenReturn(Optional.of(share));

        // when
        Share result = searchShareService.getShare("uuid");

        // then
        assertEquals(result, share);
    }

    @Test
    @DisplayName("일치하는 uuid의 Share가 없다면 NotFoundException을 던진다.")
    void uuidNotFoundException() {
        // given
        when(shareQueryPort.findById(anyString())).thenReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> searchShareService.getShare("uuid"));
    }
}
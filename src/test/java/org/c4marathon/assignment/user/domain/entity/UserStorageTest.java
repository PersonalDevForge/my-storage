package org.c4marathon.assignment.user.domain.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserStorageTest {

    @Test
    void addUsageValid() {
        // given
        UserStorage userStorage = UserStorage.of(1L, null, 100L, 0L);

        // when
        userStorage.addUsage(25L);

        // then
        assertThat(userStorage.getCurrentUsage()).isEqualTo(25L);
    }

    @Test
    void addUsageInvalidCapacityExceed() {
        // given
        UserStorage userStorage = UserStorage.of(1L, null, 100L, 0L);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> userStorage.addUsage(101L));
    }

    @Test
    void addUsageInvalidNegativeUsage() {
        // given
        UserStorage userStorage = UserStorage.of(1L, null, 100L, 50L);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> userStorage.addUsage(-51L));
    }

    @Test
    void isStorageCapacityExceeded() {
        // given
        Long capacity = 100L;
        Long currentUsage = 50L;
        Long exceedAmount = 51L;
        Long notExceedAmount = 49L;

        // when
        Boolean isExceeded = UserStorage.isStorageCapacityExceeded(capacity, currentUsage, exceedAmount);
        Boolean isNotExceeded = UserStorage.isStorageCapacityExceeded(capacity, currentUsage, notExceedAmount);

        // then
        assertThat(isExceeded).isEqualTo(true);
        assertThat(isNotExceeded).isEqualTo(false);
    }

    @Test
    void testIsStorageCapacityExceeded() {
        // given
        UserStorage userStorage = UserStorage.of(1L, null, 100L, 50L);
        Long exceedAmount = 51L;
        Long notExceedAmount = 49L;

        // when
        Boolean isExceeded = UserStorage.isStorageCapacityExceeded(userStorage, exceedAmount);
        Boolean isNotExceeded = UserStorage.isStorageCapacityExceeded(userStorage, notExceedAmount);

        // then
        assertThat(isExceeded).isEqualTo(true);
        assertThat(isNotExceeded).isEqualTo(false);
    }

}
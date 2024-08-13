package org.c4marathon.assignment.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.c4marathon.assignment.global.aop.BaseTimeEntity;

@Getter
@Entity
@Table(name = "MS_USER_STORAGE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserStorage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long capacity;

    private Long currentUsage;

    public static UserStorage of(Long id, User user, Long capacity, Long currentUsage) {
        return new UserStorage(id, user, capacity, currentUsage);
    }

}

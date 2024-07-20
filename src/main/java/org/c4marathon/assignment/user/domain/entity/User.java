package org.c4marathon.assignment.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "MS_USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;

    private String password;

    private User(String email, String nickname, String password) {
        this.id = null;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public static User of(String email, String nickname, String password) {
        return new User(email, nickname, password);
    }

}

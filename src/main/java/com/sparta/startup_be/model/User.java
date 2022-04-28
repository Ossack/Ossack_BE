
package com.sparta.startup_be.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(unique = true)
    private String userEmail;

    @Column(unique = true)
    private String nickname;

    @Column
    private String profile;

    @Column(unique = true)
    private String password;

    @Column(nullable = true, unique = true)
    private Long kakaoId;

    public User(String userEmail, String nickname, String profile, String password) {
        this.userEmail = userEmail;
        this.nickname = nickname;
        this.profile = profile;
        this.password = password;
        this.kakaoId = null;
    }

    public User(String userEmail, String nickname, String profile, String password, Long kakaoId) {
        this.userEmail = userEmail;
        this.nickname = nickname;
        this.profile = profile;
        this.password = password;
        this.kakaoId = kakaoId;
    }
}


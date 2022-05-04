
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

    public User(String userEmail, String nickname, String profile, String password) {
        this.userEmail = userEmail;
        this.nickname = nickname;
        this.profile = profile;
        this.password = password;
    }

    // 프로필 이미지 수정 시, 필요한 생성자
    public User(Long id, String imageUrl) {
        this.id = id;
        this.profile = imageUrl;
    }

    // 이미지 수정 메서드
    public void update(Long id, String imageKey) {
        this.id = id;
        this.profile = imageKey;
    }
}


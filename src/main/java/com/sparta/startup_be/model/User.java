
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

}


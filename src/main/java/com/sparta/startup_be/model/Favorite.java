package com.sparta.startup_be.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Favorite {
    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long favoriteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    public Favorite(Post post, User user) {
        this.post = post;
        this.user = user;
    }
}

package com.sparta.startup_be.dto;

import com.sparta.startup_be.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String userEmail;
    private String nickname;
    private String imageUrl;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.userEmail = user.getUserEmail();
        this.nickname = user.getNickname();
        this.imageUrl = user.getProfile();
    }
}

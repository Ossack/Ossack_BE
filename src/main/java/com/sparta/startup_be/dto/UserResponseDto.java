package com.sparta.startup_be.dto;

import com.sparta.startup_be.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private String userEmail;
    private String nickname;
    private String profile;

    public UserResponseDto(User user) {
        this.userEmail = user.getUserEmail();
        this.nickname = user.getNickname();
        this.profile = user.getProfile();
    }
}

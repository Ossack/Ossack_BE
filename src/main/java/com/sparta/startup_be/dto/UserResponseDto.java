package com.sparta.startup_be.dto;

import com.sparta.startup_be.model.User;
import com.sparta.startup_be.security.UserDetailsImpl;
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

    public UserResponseDto(UserDetailsImpl userDetails, String profile) {
        this.id = userDetails.getUser().getId();
        this.userEmail = userDetails.getUser().getUserEmail();
        this.nickname = userDetails.getUser().getNickname();
        this.imageUrl = profile;
    }
}

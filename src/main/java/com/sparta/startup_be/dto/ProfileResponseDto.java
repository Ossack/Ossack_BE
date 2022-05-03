package com.sparta.startup_be.dto;

import com.sparta.startup_be.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileResponseDto {
    private Long userId;
    private String profile;

    public ProfileResponseDto(User user) {
        this.userId = user.getId();
        this.profile = user.getProfile();
    }

}

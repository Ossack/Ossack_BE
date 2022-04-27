package com.sparta.startup_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    private String userEmail;
    private String nickname;
    private String password;
}
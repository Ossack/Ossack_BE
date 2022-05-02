package com.sparta.startup_be.dto;

import lombok.Getter;

@Getter
public class FavoriteResponseDto {

    private int status;
    private String msg;
    private boolean clicked;

    public FavoriteResponseDto() {}

    public FavoriteResponseDto(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public FavoriteResponseDto(int status, String msg, boolean clicked) {
        this.status = status;
        this.msg = msg;
        this.clicked = clicked;
    }
}
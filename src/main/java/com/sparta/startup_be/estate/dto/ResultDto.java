package com.sparta.startup_be.estate.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResultDto {
    private String message;

    public ResultDto(String message) {
        this.message = message;
    }
}

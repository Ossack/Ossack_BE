package com.sparta.startup_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoordinateDto {
    private float x;
    private float y;
    private Long estateid;

    public CoordinateDto(float x, float y,Long estateid){
        this.x = x;
        this.y = y;
        this.estateid= estateid;
    }
}

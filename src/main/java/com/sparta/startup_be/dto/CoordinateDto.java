package com.sparta.startup_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoordinateDto {
    private double x;
    private double y;
    private Long estateid;

    public CoordinateDto(double x, double y,Long estateid){
        this.x = x;
        this.y = y;
        this.estateid= estateid;
    }
}

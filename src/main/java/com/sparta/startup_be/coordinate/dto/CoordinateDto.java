package com.sparta.startup_be.coordinate.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoordinateDto {
    private double x;
    private double y;
    private Long id;

    public CoordinateDto(double x, double y,Long id){
        this.x = x;
        this.y = y;
        this.id= id;
    }
    public CoordinateDto(CoordinateResponseDto coordinateResponseDto,Long id){
        this.y = coordinateResponseDto.getLat();
        this.x = coordinateResponseDto.getLng();
        this.id= id;

    }
}

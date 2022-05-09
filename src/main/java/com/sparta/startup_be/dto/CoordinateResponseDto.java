package com.sparta.startup_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoordinateResponseDto {
    private double lat;
    private double lng;

    public CoordinateResponseDto(double lat,double lng){
        this.lat = lat;
        this.lng = lng;
    }
}

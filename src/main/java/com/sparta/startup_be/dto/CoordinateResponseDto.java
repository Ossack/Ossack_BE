package com.sparta.startup_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoordinateResponseDto {
    private float lat;
    private float lng;

    public CoordinateResponseDto(float lat,float lng){
        this.lat = lat;
        this.lng = lng;
    }
}

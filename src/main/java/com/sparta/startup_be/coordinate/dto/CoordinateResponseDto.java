package com.sparta.startup_be.coordinate.dto;

import com.sparta.startup_be.model.Coordinate;
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

    public CoordinateResponseDto(Coordinate coordinate){
        this.lat = coordinate.getY();
        this.lng = coordinate.getX();
    }
}

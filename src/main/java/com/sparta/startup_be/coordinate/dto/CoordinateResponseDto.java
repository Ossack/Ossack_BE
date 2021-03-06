package com.sparta.startup_be.coordinate.dto;

import com.sparta.startup_be.model.CoordinateEstate;
import com.sparta.startup_be.model.CoordinateSharedOffice;
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

    public CoordinateResponseDto(CoordinateEstate coordinateEstate){
        this.lat = coordinateEstate.getY();
        this.lng = coordinateEstate.getX();
    }

    public CoordinateResponseDto(CoordinateSharedOffice coordinateSharedOffice){
        this.lat = coordinateSharedOffice.getY();
        this.lng = coordinateSharedOffice.getX();
    }
}

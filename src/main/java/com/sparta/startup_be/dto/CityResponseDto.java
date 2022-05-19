package com.sparta.startup_be.dto;

import com.sparta.startup_be.model.Coordinate;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CityResponseDto {
    private String title;

    private String average;
    private CoordinateResponseDto coordinate;

    private int estate_cnt;


    public CityResponseDto(String title, CoordinateResponseDto coordinate, int estate_cnt,int average){
        this.title = title;
        this.coordinate = coordinate;
        this.estate_cnt = estate_cnt;
        this.average = ""+average;
    }
}

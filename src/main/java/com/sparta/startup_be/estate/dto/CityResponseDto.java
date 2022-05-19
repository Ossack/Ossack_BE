package com.sparta.startup_be.estate.dto;

import com.sparta.startup_be.coordinate.dto.CoordinateResponseDto;
import lombok.Getter;
import lombok.Setter;

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

package com.sparta.startup_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class MapResponseDto {
    private int level;
    private List<CityResponseDto> cityResponseDtoList;

    public MapResponseDto(int level, List<CityResponseDto> cityResponseDtoList){
        this.level = level;
        this.cityResponseDtoList = cityResponseDtoList;
    }
}

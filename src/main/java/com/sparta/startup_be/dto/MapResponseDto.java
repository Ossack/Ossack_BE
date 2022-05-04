package com.sparta.startup_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MapResponseDto {
    private int level;
    private List<CityResponseDto> cityResponseDtoList;
}

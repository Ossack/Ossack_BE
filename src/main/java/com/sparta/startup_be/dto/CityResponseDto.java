package com.sparta.startup_be.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityResponseDto {
    private String cityname;
    private int deposit; //보증금
    private int rent_fee; //월세


    public CityResponseDto(String cityname , int deposit , int rent_fee) {
        this.cityname = cityname;
        this.deposit = deposit;
        this.rent_fee = rent_fee;
    }
}

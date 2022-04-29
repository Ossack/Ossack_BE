package com.sparta.startup_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfficeResponseDto {
    private int area; //평수
    private int deposit; //보증급
    private int rent_fee; //월세

    public OfficeResponseDto(int area, int deposit , int rent_fee){
        this.area = area;
        this.deposit = deposit;
        this.rent_fee = rent_fee;
    }
}

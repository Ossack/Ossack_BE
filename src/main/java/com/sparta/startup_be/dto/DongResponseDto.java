package com.sparta.startup_be.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DongResponseDto {
    private String dongname; //동이름
    private int deposit; //보증금
    private int rent_fee; //월세

    public DongResponseDto(String dongname , int deposit , int rent_fee) {
        this.dongname = dongname;
        this.deposit = deposit;
        this.rent_fee = rent_fee;
    }
}

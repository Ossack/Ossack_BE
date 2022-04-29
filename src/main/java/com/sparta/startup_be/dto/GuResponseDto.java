package com.sparta.startup_be.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuResponseDto {
    private String guname; //구이름
    private int deposit;  //보증금
    private int rent_fee; //월세

    public GuResponseDto(String guname , int deposit , int rent_fee){
        this.guname = guname;
        this.deposit = deposit;
        this.rent_fee = rent_fee;
    }
}

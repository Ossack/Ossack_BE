package com.sparta.startup_be.dto;

import com.sparta.startup_be.model.Estate;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EstateResponseDto {
    private Long estateid;
    private String title;
    private String type;
    private String monthly;
    private String deposit;
    private int rent_fee;
    private String buildingFloor;
    private String roomFloor;
    private String buildingInfo;
    private String area;
    private List<String> subways;
    private List<String> images;
    private boolean mylike;

    public EstateResponseDto(Estate estate,boolean mylike){
        this.estateid= estate.getId();
        this.title = estate.getCity();
        this.type = estate.getType();
        this.monthly = estate.getMonthly();
        this.deposit = estate.getDeposit();
        this.rent_fee = estate.getRent_fee();
        this.buildingFloor = estate.getBuildingFloor();
        this.roomFloor = estate.getRoomFloor();
        this.buildingInfo = estate.getBuildingInfo();
        this.area = estate.getArea();
        this.subways = estate.getSubwaylist();
        this.images =estate.getImageList();
        this.mylike = mylike;
    }
}

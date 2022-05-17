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
    private String rent_fee;
    private String buildingFloor;
    private String roomFloor;
    private String buildingInfo;
    private String area;
    private String subwayInfo;
    private List<String> images;
    private boolean mylike;
    private String buildingDetail;
    private String agent;

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
        this.subwayInfo = estate.getSubwayInfo();
        this.images =estate.getImageList();
        this.mylike = mylike;
        this.buildingDetail = estate.getBuildingDetail();
        this.agent = estate.getAgent();
    }

    public EstateResponseDto(Estate estate, String city, boolean mylike){
        this.estateid= estate.getId();
        this.title = city;
        this.type = estate.getType();
        this.monthly = estate.getMonthly();
        this.deposit = estate.getDeposit();
        this.rent_fee = estate.getRent_fee();
        this.buildingFloor = estate.getBuildingFloor();
        this.roomFloor = estate.getRoomFloor();
        this.buildingInfo = estate.getBuildingInfo();
        this.area = estate.getArea();
        this.subwayInfo = estate.getSubwayInfo();
        this.images =estate.getImageList();
        this.buildingDetail = estate.getBuildingDetail();
        this.mylike = mylike;
        this.agent = estate.getAgent();

    }

}

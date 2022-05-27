package com.sparta.startup_be.estate.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.sparta.startup_be.coordinate.dto.CoordinateResponseDto;
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
    private int deposit;
    private int rent_fee;
    private String buildingFloor;
    private String roomFloor;
    private String buildingInfo;
    private String area;
    private String subwayInfo;
    private List<String> images;
    private boolean mylike;
    private String buildingDetail;
    private String agent;
    private String address;
    private String management_fee;
    private String toilet;
    private String parking;
    private String capacity;
    private String personIncharge;
    private String phoneNumber;
    private String agentNumber;

    private CoordinateResponseDto coordinateResponseDto;

    @QueryProjection
    public EstateResponseDto(Estate estate,boolean mylike){
        this.management_fee = estate.getManagement_fee();
        this.toilet = estate.getToilet();
        this.parking = estate.getParking();
        this.capacity = estate.getCapacity();
        this.personIncharge = estate.getPersonIncharge();
        this.phoneNumber = estate.getPhoneNumber();
        this.agentNumber = estate.getAgentNumber();

        this.estateid= estate.getId();
        this.title = estate.getCity();
        this.type = estate.getType();
        this.address = estate.getCity()+" "+ estate.getGu()+" "+estate.getDong();
        this.monthly = estate.getMonthly();
        this.deposit = estate.getDeposit();
        this.rent_fee = estate.getRent_fee();
        this.buildingFloor = estate.getBuildingFloor();
        this.roomFloor = estate.getRoomFloor();
        this.buildingInfo = estate.getBuildingInfo();
//        this.area =estate.getArea();
        this.area =          Integer.parseInt(String.valueOf(Math.round(Double.parseDouble(estate.getArea().split("㎡")[0])/3.3)))+"평형";
//        this.area = Double.parseDouble(estate.getArea().split("㎡")[0])/3.3+"평형";

        this.subwayInfo = estate.getSubwayInfo();

        this.images =estate.getImageList();
        this.mylike = mylike;
        this.buildingDetail = estate.getBuildingDetail();
        this.agent = estate.getAgent();
    }

    public EstateResponseDto(Estate estate,boolean mylike,CoordinateResponseDto coordinateResponseDto){
        this.address = estate.getCity()+" "+ estate.getGu()+" "+estate.getDong();
        this.estateid= estate.getId();
        this.title = estate.getCity();
        this.type = estate.getType();
        this.monthly = estate.getMonthly();
        this.deposit = estate.getDeposit();
        this.rent_fee = estate.getRent_fee();
        this.buildingFloor = estate.getBuildingFloor();
        this.roomFloor = estate.getRoomFloor();
        this.buildingInfo = estate.getBuildingInfo();
        this.coordinateResponseDto =coordinateResponseDto;
        this.area =Integer.parseInt(String.valueOf(Math.round(Double.parseDouble(estate.getArea().split("㎡")[0])/3.3)))+"평형";
        this.subwayInfo = estate.getSubwayInfo();
        this.images =estate.getImageList();
        this.mylike = mylike;
        this.buildingDetail = estate.getBuildingDetail();
        this.agent = estate.getAgent();
        this.management_fee = estate.getManagement_fee();
        this.toilet = estate.getToilet();
        this.parking = estate.getParking();
        this.capacity = estate.getCapacity();
        this.personIncharge = estate.getPersonIncharge();
        this.phoneNumber = estate.getPhoneNumber();
        this.agentNumber = estate.getAgentNumber();
    }

    public EstateResponseDto(Estate estate, String city, boolean mylike,CoordinateResponseDto coordinateResponseDto){
        this.address = estate.getCity()+" "+ estate.getGu()+" "+estate.getDong();
        this.estateid= estate.getId();
        this.title = city;
        this.type = estate.getType();
        this.monthly = estate.getMonthly();
        this.deposit = estate.getDeposit();
        this.rent_fee = estate.getRent_fee();
        this.buildingFloor = estate.getBuildingFloor();
        this.roomFloor = estate.getRoomFloor();
        this.coordinateResponseDto =coordinateResponseDto;
        this.buildingInfo = estate.getBuildingInfo();
        this.area =Integer.parseInt(String.valueOf(Math.round(Double.parseDouble(estate.getArea().split("㎡")[0])/3.3)))+"평형";
        this.subwayInfo = estate.getSubwayInfo();
//        this.area = Double.parseDouble(estate.getArea().split("㎡")[0])/3.3+"평형";

        this.images =estate.getImageList();
        this.buildingDetail = estate.getBuildingDetail();
        this.mylike = mylike;
        this.agent = estate.getAgent();
        this.management_fee = estate.getManagement_fee();
        this.toilet = estate.getToilet();
        this.parking = estate.getParking();
        this.capacity = estate.getCapacity();
        this.personIncharge = estate.getPersonIncharge();
        this.phoneNumber = estate.getPhoneNumber();
        this.agentNumber = estate.getAgentNumber();
    }


}

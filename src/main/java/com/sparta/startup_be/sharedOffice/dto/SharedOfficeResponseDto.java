package com.sparta.startup_be.sharedOffice.dto;

import com.sparta.startup_be.coordinate.dto.CoordinateResponseDto;
import com.sparta.startup_be.model.SharedOffice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SharedOfficeResponseDto {
    private Long shareofficeid;
    private String name;
    private String subwayInfo;
    private String price;
    private String time;
    private String minimum_days;
    private String floor;
    private String parking;
    private String detail;
    private List<String> imageList;
    private List<String> convience;
    private String city;
    private String gu;
    private String dong;
    private String personIncharge;
    private String number;
    private String agent;
    private CoordinateResponseDto coordinateResponseDto;
    private boolean mylike;
    private String address;


    public SharedOfficeResponseDto(SharedOffice sharedOffice, String query, boolean mylike, CoordinateResponseDto coordinateResponseDto){
        this.shareofficeid =sharedOffice.getId();
        this.name =sharedOffice.getName();
        this.subwayInfo =sharedOffice.getSubwayInfo();
        this.price =sharedOffice.getPrice();
        this.time = sharedOffice.getTime();
        this.minimum_days = sharedOffice.getMinimum_days();
        this.floor = sharedOffice.getFloor();
        this.parking = sharedOffice.getParking();
        this.detail = sharedOffice.getDetail();
        this.imageList = sharedOffice.getImageList();
        this.convience = sharedOffice.getConvience();
        this.city = sharedOffice.getCity();
        this.gu = sharedOffice.getGu();
        this.dong = sharedOffice.getDong();
        this.personIncharge = sharedOffice.getPersonIncharge();
        this.number = sharedOffice.getNumber();
        this.agent = sharedOffice.getAgent();
        this.coordinateResponseDto =coordinateResponseDto;
        this.mylike = mylike;
        this.address = sharedOffice.getCity()+" "+ sharedOffice.getGu() +" "+ sharedOffice.getDong();
    }

    public SharedOfficeResponseDto(SharedOffice sharedOffice, boolean mylike){
        this.shareofficeid =sharedOffice.getId();
        this.name =sharedOffice.getName();
        this.subwayInfo =sharedOffice.getSubwayInfo();
        this.price =sharedOffice.getPrice();
        this.time = sharedOffice.getTime();
        this.minimum_days = sharedOffice.getMinimum_days();
        this.floor = sharedOffice.getFloor();
        this.parking = sharedOffice.getParking();
        this.detail = sharedOffice.getDetail();
        this.imageList = sharedOffice.getImageList();
        this.convience = sharedOffice.getConvience();
        this.city = sharedOffice.getCity();
        this.gu = sharedOffice.getGu();
        this.dong = sharedOffice.getDong();
        this.personIncharge = sharedOffice.getPersonIncharge();
        this.number = sharedOffice.getNumber();
        this.agent = sharedOffice.getAgent();
        this.mylike = mylike;
        this.address = sharedOffice.getCity()+" "+ sharedOffice.getGu() +" "+ sharedOffice.getDong();
    }
}

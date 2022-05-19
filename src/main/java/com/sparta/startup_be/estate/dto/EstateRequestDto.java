package com.sparta.startup_be.estate.dto;

import com.sparta.startup_be.model.Estate;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class EstateRequestDto {

    private Long id;
    private String city;
    private String type;
    private String monthly;
    private String office;
    private String area;
    private String buildingInfo;
    private String deposit;
    private String rent_fee;
    private String buildingFloor;
    private String roomFloor;
    private List<String> imageList;
    private String subwayInfo;
    private String buildingDetail;
    private String agent;
    private String elevator;
    private String date;
    private String management_fee;
    private String toilet;
    private String parking;
    private String capacity;
    private String personIncharge;
    private String phoneNumber;
    private String agentNumber;
//    private String ci
}

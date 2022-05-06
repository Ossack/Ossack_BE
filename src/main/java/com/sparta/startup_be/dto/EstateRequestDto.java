package com.sparta.startup_be.dto;

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
    private String area;
    private String buildingInfo;
    private String deposit;
    private String rent_fee;
    private String buildingFloor;
    private String roomFloor;
    private List<String> imageList;
    private List<String> subwayList;
//    private String ci

}

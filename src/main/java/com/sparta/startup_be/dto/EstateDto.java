package com.sparta.startup_be.dto;

import com.sparta.startup_be.model.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
public class EstateDto {
    private Long id;
    private String city;
    private String type;
    private String monthly;
    private String area;
    private String buildingInfo;
    private String deposit;
    private int rent_fee;
    private int buildingFloor;
    private int roomFloor;
    private List<Image> imageList;
//    private String ci
}

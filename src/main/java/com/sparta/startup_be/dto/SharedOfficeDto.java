package com.sparta.startup_be.dto;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.GeneratedValue;
import java.util.List;

@Getter
@Builder
public class SharedOfficeDto {
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
}

package com.sparta.startup_be.crawling;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NemoApiDto {
    private String name;
    private String price;
    private String time;
    private String minimum_days;
    private String floor;
    private String parking;
    private String number;
    private String address;
    private Double lat;
    private Double lng;
    private List<Object> imageList;
    private String subway ;

    public NemoApiDto(JSONObject jsonObject){
        this.name = jsonObject.getString("branchName");
        this.price = "월" + jsonObject.getString("minimumRentPrice")+"~";
        this.imageList = jsonObject.getJSONArray("allPreviewPhotoUrls").toList();
        this.lat = jsonObject.getDouble("latitude");
        this.lng = jsonObject.getDouble("longitude");
        this.address = jsonObject.getString("shortAddress");
        this.number = jsonObject.getString("operatorPhoneNumber");
        this.subway =jsonObject.getString("locationInfo");
    }
}

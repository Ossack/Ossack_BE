package com.sparta.startup_be.dto;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

@Getter
@Setter
public class SeoulDto {
    private String address;
    private String type;
    private String deposit;
    private String rent_fee;
    private double area;

    public SeoulDto(JSONObject itemJson){
        this.address = itemJson.getString("SGG_NM")+" "+itemJson.getString("SGG_NM")+" "+itemJson.getString("SGG_NM");
    }
}

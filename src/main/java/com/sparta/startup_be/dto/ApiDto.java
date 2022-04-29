package com.sparta.startup_be.dto;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

@Getter
@Setter
public class ApiDto {
    private String address;
    private String price;

    public ApiDto(JSONObject itemJson) {
        this.price = itemJson.getInt("ASSURNC_AMT")+"만원";
        this.address = itemJson.getString("SIGUN_NM")+" "+itemJson.getString("SIGNGU_NM")+" "+itemJson.getString("LEGALDONG_NM");
    }
}

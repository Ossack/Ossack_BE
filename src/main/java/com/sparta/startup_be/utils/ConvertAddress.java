package com.sparta.startup_be.utils;

import com.sparta.startup_be.dto.CoordinateDto;
import com.sparta.startup_be.dto.EstateDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConvertAddress {

    public String convertAddress(String query ){
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK 0b1c519e27d6cea4e2da28be7bc7d1c1");
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("https://dapi.kakao.com/v2/local/search/address.json?query="+query, HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
//        System.out.println("Response status: " + status);
//        System.out.println(response);
        return response;
    }

    public CoordinateDto fromJSONtoItems(String result,Long estateid) {
        JSONObject rjson = new JSONObject(result);
        JSONArray items = rjson.getJSONArray("documents");
        JSONObject itemJson = items.getJSONObject(0);
        CoordinateDto coordinateDto = new CoordinateDto(Double.parseDouble(String.valueOf(itemJson.get("x"))),Double.parseDouble(String.valueOf(itemJson.get("y"))),estateid);
        return coordinateDto;
    }

}

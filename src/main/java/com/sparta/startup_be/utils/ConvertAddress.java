package com.sparta.startup_be.utils;

import com.sparta.startup_be.coordinate.dto.CoordinateDto;
import com.sparta.startup_be.coordinate.dto.CoordinateResponseDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ConvertAddress {

    public String convertAddress(String query){
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK 0b1c519e27d6cea4e2da28be7bc7d1c1");
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("https://dapi.kakao.com/v2/local/search/address.json?query="+query, HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        //        System.out.println("Response status: " + status);
//        System.out.println(response);
        return responseEntity.getBody();
    }

    public CoordinateDto fromJSONtoItems(String result,Long estateid) {
        JSONObject rjson = new JSONObject(result);
        JSONArray items = rjson.getJSONArray("documents");
        JSONObject itemJson = items.getJSONObject(0);
        return new CoordinateDto(Double.parseDouble(String.valueOf(itemJson.get("x"))),Double.parseDouble(String.valueOf(itemJson.get("y"))),estateid);
    }

    public CoordinateResponseDto fromJSONtoItems(String result) {
        JSONObject rjson = new JSONObject(result);
        JSONArray items = rjson.getJSONArray("documents");
        try {
            JSONObject itemJson = items.getJSONObject(0);
            return new CoordinateResponseDto(Double.parseDouble(String.valueOf(itemJson.get("y"))),Double.parseDouble(String.valueOf(itemJson.get("x"))));
        }catch(Exception e){
            e.printStackTrace();
        }

        return new CoordinateResponseDto(0,0) ;
    }

}

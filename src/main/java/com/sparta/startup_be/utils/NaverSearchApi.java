package com.sparta.startup_be.utils;

import com.sparta.startup_be.coordinate.dto.CoordinateResponseDto;
import com.sparta.startup_be.crawling.NemoApiDto;
import com.sparta.startup_be.sharedOffice.SharedOfficeRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NaverSearchApi {
    private final ConvertAddress convertAddress;
    private final SharedOfficeRepository sharedOfficeRepository;

    public JSONObject NaverSearchApi(String query) throws InterruptedException {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", "DXek8sUZZdDXxCkjF7tK");
        headers.add("X-Naver-Client-Secret", "r_McKZK5qI");
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        Thread.sleep(100);
        ResponseEntity<String> responseEntity = rest.exchange("https://openapi.naver.com/v1/search/local.json?query="+query, HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        JSONObject rjson = new JSONObject(response);
        JSONArray items = rjson.getJSONArray("items");
//        JSONArray itemJson = items.getJSONObject(1).getJSONArray("row");
        return rjson;
//        return items.getJSONObject(1).getString("address").split(" ")[2];
    }

    public String getAddress(String query) throws InterruptedException {
        JSONObject jsonObject = NaverSearchApi(query);
        if(jsonObject.getInt("total")==0){
            return query;
        }else {
            return  jsonObject.getJSONArray("items").getJSONObject(0).getString("address");
        }
    }

    public String getQuery(String query) throws InterruptedException {
        JSONObject jsonObject = NaverSearchApi(query);
        if(jsonObject.getInt("total")==0){
            return query;
        }else {
            return jsonObject.getJSONArray("items").getJSONObject(0).getString("address").split(" ")[2];
        }
    }

    public CoordinateResponseDto getCoordinate(String name) throws InterruptedException {
        String address;
        JSONObject jsonObject = NaverSearchApi(name);
        if(jsonObject.getInt("total")==0){
            address = sharedOfficeRepository.findSharedOfficeByName(name).get(0).getDong();
        }else {
            address = jsonObject.getJSONArray("items").getJSONObject(0).getString("address");
        }
        return convertAddress.fromJSONtoItems(convertAddress.convertAddress(address));
    }
}

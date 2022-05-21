
package com.sparta.startup_be.utils;


import com.sparta.startup_be.crawling.ApiDto;
import com.sparta.startup_be.crawling.NemoApiDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class NemoApi {

    public String nemoApi(){
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("https://www.nemoapp.kr/api/coworking-space-branches/search/?IsFeatured=&PageIndex=0&Id=&Building=&Region=&SectorType=&MRentMin=&MRentMax=&HasConference=&HasMailbox=&HasWifi=&HasSnack=&HasCoffee=&HasBusinessSupport=&SWLng=0&SWLat=0&NELng=130&NELat=40&Zoom=17", HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
//        System.out.println("Response status: " + status);
//        System.out.println(response);
        return response;
    }

    public List<NemoApiDto> fromJSONtoItems(String result) {
        JSONObject rjson = new JSONObject(result);
        JSONArray items = rjson.getJSONArray("items");
        List<NemoApiDto> nemoApiDtos = new ArrayList<>();
//        JSONArray itemJson = items.getJSONObject(1).getJSONArray("row");
        for (int i = 0; i < items.length(); i++) {
            System.out.println(items.getJSONObject(i).getString("minimumRentPrice"));
            NemoApiDto nemoApiDto = new NemoApiDto(items.getJSONObject(i));
        }
        System.out.println(nemoApiDtos);
        return nemoApiDtos;
    }
}

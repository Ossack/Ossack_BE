package com.sparta.startup_be.utils;

import com.sparta.startup_be.dto.ApiDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class SearchApi {

    public String searchApi() {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("https://openapi.gg.go.kr/Effaptlesrentstatmnt?Type=json&KEY=1a20d9a4edcc433aa3ea88da6e709327", HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        System.out.println("Response status: " + status);
        System.out.println(response);
        return response;
    }

    public List<ApiDto> fromJSONtoItems(String result) {
        JSONObject rjson = new JSONObject(result);
        JSONArray items = rjson.getJSONArray("Effaptlesrentstatmnt");
        List<ApiDto> apiDtos = new ArrayList<>();
        JSONArray itemJson = items.getJSONObject(1).getJSONArray("row");
        for (int i = 0; i < itemJson.length(); i++) {
//            System.out.println(itemJson.get(i).getClass().getName());

            ApiDto apiDto = new ApiDto(itemJson.getJSONObject(i));
            apiDtos.add(apiDto);
        }
        System.out.println(apiDtos);
        return apiDtos;
    }
}

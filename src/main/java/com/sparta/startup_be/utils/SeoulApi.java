package com.sparta.startup_be.utils;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SeoulApi {

    public String SeoulApi(){
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("http://openapi.seoul.go.kr:8088/48766c71656c697435394f59566248/json/houseRentPriceInfo/1/20/", HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        System.out.println("Response status: " + status);
        System.out.println(response);
        return response;
    }

//    public List<ApiDto> fromJSONtoItems(String result) {
//        JSONObject rjson = new JSONObject(result);
//        JSONArray items = rjson.getJSONArray("houseRentPriceInfo");
//        List<ApiDto> apiDtos = new ArrayList<>();
//        for (int i = 0; i < itemJson.length(); i++) {
////            System.out.println(itemJson.get(i).getClass().getName());
//            JSONObject itemJson = items.getJSONObject(i);
//            ApiDto apiDto = new ApiDto(itemJson.getJSONObject(i));
//            apiDtos.add(apiDto);
//        }
//        System.out.println(apiDtos);
//        return apiDtos;
//    }
    //  public List<SubDto> fromJSONtoItems(String result) {
    //        JSONObject rjson = new JSONObject(result);
    //        JSONArray items = rjson.getJSONArray("realtimeArrivalList");
    //        List<SubDto> subDtoList = new ArrayList<>();
    //        for (int i = 0; i < items.length(); i++) {
    //            JSONObject itemJson = items.getJSONObject(i);
    //            SubDto subDto = new SubDto(itemJson);
    //            subDtoList.add(subDto);
    //        }
    //        System.out.println(subDtoList);
    //        return subDtoList;
    //    }
}

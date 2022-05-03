package com.sparta.startup_be.controller;

import com.sparta.startup_be.dto.CoordinateDto;
import com.sparta.startup_be.model.Estate;
import com.sparta.startup_be.service.EstateService;
import com.sparta.startup_be.utils.ConvertAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class EstateController {
    private final EstateService estateService;
    private final ConvertAddress convertAddress;

//    @GetMapping("/api/show")
//    private List<Estate> show(){
//        return estateService.show();
//    }

    @GetMapping("/api/showmore")
    private String average(@RequestParam String query){
        System.out.println(query);
        return estateService.average(query);
    }

    @GetMapping("/api/city/gu")
    private List<Estate> ugAverage(){
        return estateService.guAverage();
    }

//    @GetMapping("/api/city/hi")
//    private List<CoordinateDto> convert(){
//        String resultString = convertAddress.convertAddress();
//        return convertAddress.fromJSONtoItems(resultString);
//    }
}

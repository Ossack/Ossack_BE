package com.sparta.startup_be.controller;

import com.sparta.startup_be.dto.CoordinateDto;


import com.sparta.startup_be.dto.FavoriteListDto;

import com.sparta.startup_be.dto.MapResponseDto;

import com.sparta.startup_be.model.Estate;
import com.sparta.startup_be.security.UserDetailsImpl;
import com.sparta.startup_be.service.EstateService;
import com.sparta.startup_be.utils.ConvertAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private List<Estate> ugAverage(@RequestParam String query){
        return estateService.guAverage(query);
    }

    //지도조회
    @GetMapping("/api/{level}/map")
    private MapResponseDto showEstate(@RequestParam float SWlat, @RequestParam float SWlng, @RequestParam float NElat, @RequestParam float NElng,
                                      @PathVariable int level, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return estateService.showEstate(SWlng,NElng,SWlat,NElat,level,userDetails);
    }

//    @GetMapping("/api/city/hi")
//    private List<CoordinateDto> convert(){
//        String resultString = convertAddress.convertAddress();
//        return convertAddress.fromJSONtoItems(resultString);
//    }

    @GetMapping("/api/list/favorite")
    public List<FavoriteListDto> showFavorite(
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ){
        return estateService.showFavorite(userDetails);
    }
}

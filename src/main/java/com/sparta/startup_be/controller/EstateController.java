package com.sparta.startup_be.controller;

import com.sparta.startup_be.dto.CityResponseDto;
import com.sparta.startup_be.dto.EstateResponseDto;
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
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class EstateController {
    private final EstateService estateService;
    private final ConvertAddress convertAddress;

//    @GetMapping("/api/show")
//    private List<Estate> show(){
//        return estateService.show();
//    }

//    @GetMapping("/api/showmore")
//    private String average(@RequestParam String query){
//        System.out.println(query);
//        return estateService.average(query);
//    }

//    @GetMapping("/api/city/gu")
//    private List<Estate> ugAverage(@RequestParam String query){
//        return estateService.guAverage(query);
//    }

    //메인 페이지 키워드 검색
    @GetMapping("/api/list")
    private List<EstateResponseDto> searchKeyword(@RequestParam String query,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return estateService.searchKeyword(query,userDetails);
    }

    //검색 후 리스트 반환
    @GetMapping("/api/list/search/{officecnt}")
    private List<EstateResponseDto> searchTowm(@RequestParam String query,@AuthenticationPrincipal UserDetailsImpl userDetails,@PathVariable int officecnt){
        return estateService.searchTowm(query,userDetails,officecnt);
    }

    //level별 지도 조회
    @GetMapping("/api/{level}/map")
    private MapResponseDto showEstate2(@RequestParam float SWlat, @RequestParam float SWlng, @RequestParam float NElat, @RequestParam float NElng,
                                      @PathVariable int level, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return estateService.showEstate(SWlng,NElng,SWlat,NElat,level,userDetails);
    }
    //리스트 선택 후 디테일 조회
    @GetMapping("/api/detail/{estateid}")
    private EstateResponseDto showDetail(@PathVariable Long estateid, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return estateService.showDetail(estateid,userDetails.getUser());
    }

    // 지금 핫한 오피스
    @GetMapping("/api/list/hot")
    private List<Map<String,Object>> showHot(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return estateService.searchHot(userDetails);
    }

    //검색 복록 지도 조회
    @GetMapping("/api/{level}/map/search")
    private CityResponseDto showSearchonMap(@PathVariable int level, @RequestParam String query, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return estateService.showSearchonMap(level,query,userDetails);
    }

//    @GetMapping("/api/city/hi")
//    private List<CoordinateDto> convert(){
//        String resultString = convertAddress.convertAddress();
//        return convertAddress.fromJSONtoItems(resultString);
//    }

    @GetMapping("/api/list/favorite")
    public List<EstateResponseDto> showFavorite(
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ){
        return estateService.showFavorite(userDetails);
    }
}

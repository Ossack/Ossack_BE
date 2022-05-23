package com.sparta.startup_be.estate;


import com.sparta.startup_be.estate.dto.EstateResponseDto;
import com.sparta.startup_be.estate.dto.MapResponseDto;
import com.sparta.startup_be.estate.dto.SearchDto;
import com.sparta.startup_be.estate.dto.CityResponseDto;
import com.sparta.startup_be.login.security.UserDetailsImpl;
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
    @GetMapping("/estates")
    private List<EstateResponseDto> searchKeyword(@RequestParam String query, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return estateService.searchKeyword(query,userDetails);
    }

    //검색 후 리스트 반환
    @GetMapping("/estates/{pagenum}")
    private SearchDto searchTowm(@RequestParam String query, @RequestParam String monthly, @AuthenticationPrincipal UserDetailsImpl userDetails,
                                 @RequestParam String depositlimit, @RequestParam String feelimit,@PathVariable int pagenum){
        return estateService.searchTowm(query,userDetails,pagenum-1,monthly,depositlimit.replace(",",""),feelimit.replace(",",""));
    }

    //level별 지도 조회(사무실, 공유오피스)
    @GetMapping("/map")
    private MapResponseDto showEstate2(@RequestParam double SWlat, @RequestParam double SWlng, @RequestParam double NElat, @RequestParam double NElng,
                                       @RequestParam int level, @RequestParam String depositlimit, @RequestParam String feelimit, @RequestParam String monthly,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails){
        return estateService.showEstate(SWlng,NElng,SWlat,NElat,level,userDetails,depositlimit,feelimit,monthly);
    }

    //검색 복록 지도 조회
    @GetMapping("/map/search")
    private CityResponseDto showSearchonMap(@RequestParam int level, @RequestParam String query, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return estateService.showSearchonMap(level,query,userDetails);
    }

    //리스트 선택 후 디테일 조회
    @GetMapping("/estate/{estateid}")
    private EstateResponseDto showDetail(@PathVariable Long estateid, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return estateService.showDetail(estateid,userDetails.getUser());
    }

    // 지금 핫한 오피스
    @GetMapping("/estates/hot")
    private List<Map<String,Object>> showHot(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return estateService.searchHot(userDetails);
    }

//    @GetMapping("/api/city/hi")
//    private List<CoordinateDto> convert(){
//        String resultString = convertAddress.convertAddress();
//        return convertAddress.fromJSONtoItems(resultString);
//    }

    @GetMapping("/estates/favorite")
    public List<EstateResponseDto> showFavorite(
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ){
        return estateService.showFavorite(userDetails);
    }
}

package com.sparta.startup_be.sharedOffice;

import com.sparta.startup_be.estate.dto.MapResponseDto;
import com.sparta.startup_be.login.security.UserDetailsImpl;
import com.sparta.startup_be.sharedOffice.dto.SearchSharedOfficeResponseDto;
import com.sparta.startup_be.sharedOffice.dto.SharedOfficeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SharedOfficeController {
    private final SharedOfficeService sharedOfficeService;

    //위도 경도별 지도 조회
    @GetMapping("/map/sharedoffice")
    private MapResponseDto showSharedOffice(@RequestParam float SWlat, @RequestParam float SWlng, @RequestParam float NElat, @RequestParam float NElng,
                                       @RequestParam int level, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return sharedOfficeService.showSharedOffice(SWlng,NElng,SWlat,NElat,level,userDetails);
    }

    //해당 지역 조회
    @GetMapping("/sharedoffices")
    private SearchSharedOfficeResponseDto searchTowm(@RequestParam String query, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam Integer pagenum){
        System.out.println(query);
        return sharedOfficeService.searchTowm(query,userDetails,pagenum-1);
    }

    //해당 매물 조회
    @GetMapping("/sharedoffice/{sharedofficeid}")
    private SharedOfficeResponseDto showdetail(@PathVariable Long sharedofficeid,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return sharedOfficeService.showdetail(sharedofficeid,userDetails);
    }

    //찜한 오피스 보기
    @GetMapping("/sharedoffices/favorite")
    private List<SharedOfficeResponseDto> showmySharedOffice(@AuthenticationPrincipal UserDetailsImpl userDetail){
        return sharedOfficeService.showMySharedOffice(userDetail.getUser());
    }

    @GetMapping("/sharedoffice/favorite")
    public List<SharedOfficeResponseDto> showFavorite(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        return sharedOfficeService.showFavorite(userDetails);
    }

//    @GetMapping("/map/sharedoffices/{level}")
//    public List
}

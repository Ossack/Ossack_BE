package com.sparta.startup_be.controller;


import com.sparta.startup_be.security.UserDetailsImpl;
import com.sparta.startup_be.service.FavoriteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@Api(tags = {"관심있는 매물 찜하기 기능 Controller"})
@RequiredArgsConstructor
@RestController
public class FavoriteController {
    private final FavoriteService favoriteService;

    @ExceptionHandler(IllegalArgumentException.class)
    public String nullex(IllegalArgumentException e) {
        return e.getMessage();
    }

    @ApiOperation(value = "좋아요를 누를떄 사용하는 메소드")
    @ApiImplicitParam(name = "estateid" , value = "좋아요 누를 매물의 매물ID" )
    @PostMapping("/api/favorite/{estateid}")
    public String pressLike(@PathVariable Long estateid,
                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long userid = userDetails.getUser().getId();
        favoriteService.pressLike(estateid,userid);
        return "true";
    }
    @ApiOperation(value = "좋아요를 한번더눌러서 취소할때 사용하는 메소드")
    @ApiImplicitParam(name = "estateid" , value = "좋아요 누를 매물의 매물ID" )
    @DeleteMapping("/api/favorite/{estateid}")
    public String unpresslike(@PathVariable Long estateid,
                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long userid = userDetails.getUser().getId();
        favoriteService.unpressLike(estateid,userid);
        return "false";
    }
}

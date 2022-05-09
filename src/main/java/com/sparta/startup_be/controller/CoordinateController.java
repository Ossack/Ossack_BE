package com.sparta.startup_be.controller;

import com.sparta.startup_be.service.CoordinateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api (tags = {"좌표 계산하는 기능 Controller"})
@RestController
@RequiredArgsConstructor
public class CoordinateController {
    private final CoordinateService coordinateService;

    @ApiOperation(value = "좌표계산 메소드")
    @PostMapping("/api/convertAddress")
    private String convertAddress(){
        coordinateService.convertAddress();
        return "성공";
    }
}

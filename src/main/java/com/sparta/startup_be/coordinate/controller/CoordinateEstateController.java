package com.sparta.startup_be.coordinate.controller;

import com.sparta.startup_be.coordinate.service.CoordinateEstateService;
import com.sparta.startup_be.estate.EstateRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api (tags = {"좌표 계산하는 기능 Controller"})
@RestController
@RequiredArgsConstructor
public class CoordinateEstateController {
    private final CoordinateEstateService coordinateEstateService;

    @ApiOperation(value = "좌표계산 메소드")
    @PostMapping("/estate/convertAddress")
    private String convertAddress() {
        coordinateEstateService.convertAddress();
        return "성공";
    }

}

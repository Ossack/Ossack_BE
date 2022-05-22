package com.sparta.startup_be.coordinate.controller;


import com.sparta.startup_be.coordinate.service.CoordinateEstateService;
import com.sparta.startup_be.coordinate.service.CoordinateSharedOfficeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CoordinateSharedOfficeController {
    private final CoordinateSharedOfficeService coordinateSharedOfficeService;

    @ApiOperation(value = "좌표계산 메소드")
    @PostMapping("/sharedoffice/convertAddress")
    private String convertAddress() {
        coordinateSharedOfficeService.convertAddress();
        return "성공";
    }

}

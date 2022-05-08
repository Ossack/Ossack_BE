package com.sparta.startup_be.controller;

import com.sparta.startup_be.repository.EstateRepository;
import com.sparta.startup_be.service.CoordinateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CoordinateController {
    private final CoordinateService coordinateService;
    private final EstateRepository estateRepository;

    @PostMapping("/api/convertAddress")
    private String convertAddress() {
        coordinateService.convertAddress();
        return "성공";
    }

}

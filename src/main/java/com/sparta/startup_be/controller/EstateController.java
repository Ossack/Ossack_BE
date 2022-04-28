package com.sparta.startup_be.controller;

import com.sparta.startup_be.model.Estate;
import com.sparta.startup_be.service.EstateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class EstateController {
    private final EstateService estateService;

    @GetMapping("/api/show")
    private List<Estate> show(){
        return estateService.show();
    }
}

package com.sparta.startup_be.controller;

import com.sparta.startup_be.utils.WebDriverUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CrawlingEx {

    @GetMapping("/api/ex")
    public void example(){
        WebDriverUtil webDriverUtil = new WebDriverUtil();
        webDriverUtil.useDriver("https://m.land.naver.com/map/37.5613878:126.9251699:15:/OPST/A1:B1:B2:B3#mapFullList");
    }
}

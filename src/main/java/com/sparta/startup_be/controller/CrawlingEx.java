//
//package com.sparta.startup_be.controller;
//
//import com.sparta.startup_be.service.EstateService;
//import com.sparta.startup_be.utils.WebDriverUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//public class CrawlingEx {
//    private final EstateService estateService;
//
//    @GetMapping("/api/ex")
//    public void example() throws InterruptedException {
//        WebDriverUtil webDriverUtil = new WebDriverUtil();
//        estateService.storeEstate(webDriverUtil.useDriver("https://m.land.naver.com/map/37.5587991:126.9217943:18:/SMS/A1:B1:B2:B3#mapFullList"));
//    }
//
//    @GetMapping("/api/nemo")
//    public void exampleNemo() throws InterruptedException {
//        WebDriverUtil webDriverUtil = new WebDriverUtil();
//       estateService.storeEstate(webDriverUtil.useDriverNemo());
//    }
//}


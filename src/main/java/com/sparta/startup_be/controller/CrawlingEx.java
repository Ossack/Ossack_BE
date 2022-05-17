
package com.sparta.startup_be.controller;

import com.sparta.startup_be.service.EstateService;
import com.sparta.startup_be.utils.WebDriverUtil;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api (tags = {"매물정보 크롤링 하는 기능 Controller"})
@RestController
@RequiredArgsConstructor
public class CrawlingEx {
    private final EstateService estateService;

    @GetMapping("/api/ex")
    public void example() throws InterruptedException {
        //Multi Thread webdriver 선언
        WebDriverUtil webDriverUtil1 = new WebDriverUtil(1);
        WebDriverUtil webDriverUtil2 = new WebDriverUtil(2);
        WebDriverUtil webDriverUtil3 = new WebDriverUtil(3);
//        WebDriverUtil webDriverUtil4 = new WebDriverUtil(4);
//        WebDriverUtil webDriverUtil5 = new WebDriverUtil(5);
//        WebDriverUtil webDriverUtil6 = new WebDriverUtil(6);
//        WebDriverUtil webDriverUtil7 = new WebDriverUtil(7);
//        WebDriverUtil webDriverUtil8 = new WebDriverUtil(0);

        //Multi Thread 동작 시작
        webDriverUtil1.start();
        webDriverUtil2.start();
        webDriverUtil3.start();
//        webDriverUtil4.start();
//        webDriverUtil5.start();
//        webDriverUtil6.start();
//        webDriverUtil7.start();
//        webDriverUtil8.start();

        //MultiThread 동작 종료시 까지 대기
        webDriverUtil1.join();
        webDriverUtil2.join();
        webDriverUtil3.join();
//        webDriverUtil4.join();
//        webDriverUtil5.join();
//        webDriverUtil6.join();
//        webDriverUtil7.join();
//        webDriverUtil8.join();

        System.out.println(webDriverUtil1.getResult().size());
        System.out.println(webDriverUtil2.getResult().size());
        System.out.println(webDriverUtil3.getResult().size());
//        System.out.println(webDriverUtil4.getResult().size());
//        System.out.println(webDriverUtil5.getResult().size());
//        System.out.println(webDriverUtil6.getResult().size());
//        System.out.println(webDriverUtil7.getResult().size());
//        System.out.println(webDriverUtil8.getResult().size());

        //크롤링 결과 일괄 저장
        estateService.storeEstate(webDriverUtil1.getResult());
        estateService.storeEstate(webDriverUtil2.getResult());
        estateService.storeEstate(webDriverUtil3.getResult());
//        estateService.storeEstate(webDriverUtil4.getResult());
//        estateService.storeEstate(webDriverUtil5.getResult());
//        estateService.storeEstate(webDriverUtil6.getResult());
//        estateService.storeEstate(webDriverUtil7.getResult());
//        estateService.storeEstate(webDriverUtil8.getResult());

//        estateService.storeEstate(webDriverUtil1.useDriver("https://m.land.naver.com/map/37.5587991:126.9217943:18:/SMS/A1:B1:B2:B3#mapFullList"));
//        estateService.storeEstate(webDriverUtil2.useDriver("https://m.land.naver.com/map/37.5587991:126.9217943:18:/SMS/A1:B1:B2:B3#mapFullList"));
    }

    @GetMapping("/api/nemo")
    public void exampleNemo() throws InterruptedException {
        WebDriverUtil webDriverUtil = new WebDriverUtil(3);
       estateService.storeEstate(webDriverUtil.useDriverNemo());
    }
}


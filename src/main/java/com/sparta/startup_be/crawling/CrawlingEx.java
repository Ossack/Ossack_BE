
package com.sparta.startup_be.crawling;

import com.sparta.startup_be.coordinate.service.CoordinateEstateService;
import com.sparta.startup_be.estate.EstateService;
import com.sparta.startup_be.sharedOffice.SharedOfficeService;
import com.sparta.startup_be.utils.WebDriverUtil;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api (tags = {"매물정보 크롤링 하는 기능 Controller"})
@RestController
@RequiredArgsConstructor
public class CrawlingEx {
    private final EstateService estateService;
    private final SharedOfficeService sharedOfficeService;
    private final CoordinateEstateService coordinateEstateService;

//    @Scheduled(fixedDelay = 1000)
    @GetMapping("/api/ex")
    public void example() throws InterruptedException {
        //Multi Thread webdriver 선언
        long temp1 = System.currentTimeMillis();

        WebDriverUtil webDriverUtil1 = new WebDriverUtil(1);
        WebDriverUtil webDriverUtil2 = new WebDriverUtil(2);
        WebDriverUtil webDriverUtil3 = new WebDriverUtil(3);
        WebDriverUtil webDriverUtil4 = new WebDriverUtil(4);
//        WebDriverUtil webDriverUtil5 = new WebDriverUtil(5);
//        WebDriverUtil webDriverUtil6 = new WebDriverUtil(6);
//        WebDriverUtil webDriverUtil7 = new WebDriverUtil(7);
//        WebDriverUtil webDriverUtil8 = new WebDriverUtil(0);

        //Multi Thread 동작 시작
        webDriverUtil1.start();
        webDriverUtil2.start();
        webDriverUtil3.start();
        webDriverUtil4.start();
//        webDriverUtil5.start();
//        webDriverUtil6.start();
//        webDriverUtil7.start();
//        webDriverUtil8.start();

        //MultiThread 동작 종료시 까지 대기
        webDriverUtil1.join();
        webDriverUtil2.join();
        webDriverUtil3.join();
        webDriverUtil4.join();
//        webDriverUtil5.join();
//        webDriverUtil6.join();
//        webDriverUtil7.join();
//        webDriverUtil8.join();

        System.out.println(webDriverUtil1.getResult().size());
        System.out.println(webDriverUtil2.getResult().size());
        System.out.println(webDriverUtil3.getResult().size());
        System.out.println(webDriverUtil4.getResult().size());
//        System.out.println(webDriverUtil5.getResult().size());
//        System.out.println(webDriverUtil6.getResult().size());
//        System.out.println(webDriverUtil7.getResult().size());
//        System.out.println(webDriverUtil8.getResult().size());

        //크롤링 결과 일괄 저장
        try {
            estateService.storeEstate(webDriverUtil1.getResult());
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            estateService.storeEstate(webDriverUtil2.getResult());
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            estateService.storeEstate(webDriverUtil3.getResult());
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            estateService.storeEstate(webDriverUtil4.getResult());
        }catch (Exception e){
            e.printStackTrace();
        }
//        try {
//            estateService.storeEstate(webDriverUtil5.getResult());
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        estateService.storeEstate(webDriverUtil6.getResult());
//        estateService.storeEstate(webDriverUtil7.getResult());
//        estateService.storeEstate(webDriverUtil8.getResult());
        long temp2 = System.currentTimeMillis();
        System.out.println("총");
        System.out.println(temp2-temp1);


    }

    @GetMapping("/api/nemo")
    public void exampleNemo() throws InterruptedException {
        WebDriverUtil webDriverUtil = new WebDriverUtil(3);
       estateService.storeEstate(webDriverUtil.useDriverNemo());
    }

    @GetMapping("/api/crawling/shared")
    public void crawlingSharedOffice() throws InterruptedException {
        WebDriverUtil webDriverUtil = new WebDriverUtil(0);
        sharedOfficeService.storeSharedOffice(webDriverUtil.crawlingSharedOffice());
    }
}


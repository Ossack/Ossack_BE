package com.sparta.startup_be.utils;

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class WebDriverUtil {

    private WebDriver driver;
    public static String WEB_DRIVER_ID = "webdriver.chrome.driver"; // Properties 설정
    public static String WEB_DRIVER_PATH = "C:/Users/82103/Desktop/sparta/chromedriver.exe"; // WebDriver 경로

    public WebDriverUtil() {
        chrome();
    }

    private void chrome() {
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        // webDriver 옵션 설정.
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--lang=ko");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.setCapability("ignoreProtectedModeSettings", true);
        // weDriver 생성.
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
    }

    public void useDriver(String url) throws InterruptedException {
        Actions action = new Actions(driver);
        driver.get(url) ;
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);  // 페이지 불러오는 여유시간.
        log.info("++++++++++++++++++++++===================+++++++++++++ selenium : " + driver.getTitle());
        try{
            driver.findElement(By.className("btn_option")).click();
        }catch(Exception e) {
            e.printStackTrace();
        }
        WebElement item = driver.findElement(By.className("article_box"));
        int m = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"_listContainer\"]/div/div[1]/a/h3/strong")).getText().replace("+",""));
        int j=0;
        while(true) {

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(0, document.body.scrollHeight)", item);
            Thread.sleep(1);
            j++;
            if(j==m) break;
        }

//        int i=0;

        List<WebElement> webElements = driver.findElements(By.className("item_area"));
//        for(WebElement webElement: webElements){
//            try{
//                System.out.println(i++);
//                System.out.println(webElement.findElement(By.className("title_place")).getText()); //중소형 사무실
//                System.out.println(webElement.findElement(By.className("price_area")).getText());  //월세
//                System.out.println(webElement.findElement(By.className("information_area")).getText()); //기본정보
//                System.out.println(webElement.findElement(By.className("spec")).getText()); //스펙
//                System.out.println(webElement.findElement(By.className("agent_name")).getText()); //부동산
//            }catch(Exception e) {
//                System.out.println("\n\n\n\n\n뭐하나 없당\n\n\n\n\n");
//            }
//        }

        System.out.println(driver.findElement(By.tagName("head")).getText());

        int i=0;
        for(WebElement webElement : webElements){
            System.out.println(webElement.findElement(By.className("merit_area")).getText());
            if(!webElement.findElement(By.className("merit_area")).getText().contains("중개사")){
                webElement.click();
                Thread.sleep(1000);
                i++;
                System.out.println("i="+i);
                System.out.println("body"+driver.findElement(By.tagName("head")).getText());
                System.out.println(driver.findElement(By.className("detail_deal_kind")).getText());
                System.out.println("매물:"+driver.findElement(By.tagName("div")).getText());
//                System.out.println("타입:"+driver.findElement(By.xpath("//*[@id=\"detailMy--fixed\"]/em")).getText());
//                System.out.println("가격:"+driver.findElement(By.xpath("//*[@id=\"detailMy--fixed\"]/strong")).getText());
                driver.navigate().back();
            }
//            System.out.println("정보:"+webElement.findElement(By.className("detail_introduction_text")).getText());
//            List<WebElement> images = driver.findElements(By.className("detail_photo_inner"));
//            List<WebElement> infos = driver.findElements(By.className("detail_table_row"));
//            for(WebElement info: infos){
//                System.out.println(info.getText());
//            }
//            System.out.println("주소:"+webElement.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div[6]/div[2]/em")).getText());
//            System.out.println("부동산이름:"+webElement.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div[7]/div[1]/h3")).getText());
        }
        log.info("++++++++++++++++++++++===================+++++++++++++ 끝 : " );

        quitDriver();
    }

    private void quitDriver() {
        driver.quit(); // webDriver 종료
    }

}



package com.sparta.startup_be.utils;

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.awt.*;
import java.util.ArrayList;
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
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int i=0;
        String abc = "";
        for(WebElement webElement : webElements){
            System.out.println(webElement.findElement(By.className("merit_area")).getText());
            System.out.println(driver.findElement(By.cssSelector("iframe")).getAttribute("id"));
            System.out.println(driver.getTitle());
            if(!webElement.findElement(By.className("merit_area")).getText().contains("중개사")){
                abc = driver.getWindowHandle();
//                webElement.click();
//                System.out.println(webElement.findElement(By.className("item_link")).getAttribute("href"));
                webElement.findElement(By.className("item_link")).sendKeys(Keys.CONTROL +"\n");
                driver.switchTo().window((String) driver.getWindowHandles().toArray()[1]);
                System.out.println(driver.getWindowHandles().toArray().length);
//                System.out.println(driver.findElement(By.cssSelector("iframe")).getAttribute("id"));
                driver.switchTo().frame(driver.findElement(By.id("_newMobile")));
                Thread.sleep(1000);
                i++;
                System.out.println("i="+i);
                System.out.println("바디"+driver.findElement(By.tagName("body")).getText());
                System.out.println("매물:"+driver.findElement(By.className("detail_deal_kind")).getText());
//                System.out.println("타입:"+driver.findElement(By.xpath("//*[@id=\"detailMy--fixed\"]/em")).getText());
//                System.out.println("가격:"+driver.findElement(By.xpath("//*[@id=\"detailMy--fixed\"]/strong")).getText());
                driver.close();
//                driver.switchTo().parentFrame();
//                System.out.println(driver.findElement(By.cssSelector("iframe")).getAttribute("id"));
//                driver.switchTo().window(abc);
                driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());
//                driver.switchTo().window(allwindows.get(1));
                Thread.sleep(1000);

//                System.out.println(driver.findElement(By.cssSelector("iframe")).getAttribute("id"));
                System.out.println(driver.findElement(By.className("option_item")).getText());

            }

        }
        log.info("++++++++++++++++++++++===================+++++++++++++ 끝 : " );

        quitDriver();
    }

    private void quitDriver() {
        driver.quit(); // webDriver 종료
    }

}



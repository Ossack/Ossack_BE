package com.sparta.startup_be.utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

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


        List<WebElement> webElements = driver.findElements(By.className("item_area"));
        int i=0;
        for(WebElement webElement : webElements){
            if(!webElement.findElement(By.className("merit_area")).getText().contains("중개사")){
                webElement.findElement(By.className("item_link")).sendKeys(Keys.CONTROL +"\n");
                driver.switchTo().window((String) driver.getWindowHandles().toArray()[1]);
                System.out.println(driver.getWindowHandles().toArray().length);
                driver.switchTo().frame(driver.findElement(By.id("_newMobile")));
                Thread.sleep(1000);
                i++;
                System.out.println("i="+i);
                System.out.println("매물:"+driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div[1]/div/div[1]/div[2]/strong")).getText());
                System.out.println("전월세:"+driver.findElement(By.xpath("//*[@id=\"detailMy--fixed\"]/em")).getText());
                System.out.println("가격:"+driver.findElement(By.xpath("//*[@id=\"detailMy--fixed\"]/strong")).getText());
                System.out.println("정보:"+driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div[1]/div/div[2]/div[1]/p")).getText());
                List<WebElement> images = driver.findElements(By.className("detail_photo_item"));
                System.out.println("이미지 리스트");
                for(WebElement image : images){
                    System.out.println(image.getAttribute("aria-label style"));
                }
                System.out.println("계약/전용면적:"+driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div[2]/div[2]/div[1]/div/span[2]")).getText());
                System.out.println("몇층:"+driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div[2]/div[2]/div[3]/div[2]/span[2]")).getText());
                System.out.println("매물번호:"+driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div[2]/div[2]/div[11]/div/span[2]")).getText());
                System.out.println("지역:"+driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div[6]/div[2]/em")).getText());

                driver.close();
                driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());
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



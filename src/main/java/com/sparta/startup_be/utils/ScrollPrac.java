//package com.sparta.startup_be.utils;
//
//
//
//import lombok.var;
//import org.openqa.selenium.*;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//@SpringBootApplication
//public class ScrollPrac
//{
//    public static String WEB_DRIVER_ID = "webdriver.chrome.driver"; // Properties 설정
//    public static String WEB_DRIVER_PATH = "C:/Users/82103/Desktop/sparta/chromedriver.exe";
//
//    public static void main(String[] args) throws InterruptedException {
//        SpringApplication.run(ScrollPrac.class, args).getBean(ScrollPrac.class).test();
//    }
//
//    public void test() throws InterruptedException {
//
//        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
//        ChromeOptions options = new ChromeOptions();
//        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
//
//
//        WebDriver driver = new ChromeDriver(options);
//        WebDriver driver1 = new ChromeDriver(options);
//
//        try {
//            driver.get("https://user.tving.com/pc/user/otherLogin.tving?loginType=20&from=pc&rtUrl=https://www.tving.com&csite=&isAuto=false");
//            driver.findElement(By.xpath("//*[@id=\"a\"]")).sendKeys("ott가입아이디입력");
//            driver.findElement(By.xpath("//*[@id=\"b\"]")).sendKeys("ott가입비밀번호입력");
//            driver.findElement(By.xpath("//*[@id=\"doLoginBtn\"]")).click();
//            System.out.println("로그인 성공 = " + driver.getCurrentUrl());
//
//
//            driver1.get("https://user.tving.com/pc/user/otherLogin.tving?loginType=20&from=pc&rtUrl=https://www.tving.com&csite=&isAuto=false");
//            driver1.findElement(By.xpath("//*[@id=\"a\"]")).sendKeys("ott가입아이디입력");
//            driver1.findElement(By.xpath("//*[@id=\"b\"]")).sendKeys("ott가입비밀번호입력");
//            driver1.findElement(By.xpath("//*[@id=\"doLoginBtn\"]")).click();
//            System.out.println("로그인 성공1 = " + driver1.getCurrentUrl());
//
//
//            driver.navigate().to("https://www.tving.com"); //홈페이지
//            System.out.println("홈페이지 = " + driver.getCurrentUrl());
//
//            driver.navigate().to("https://www.tving.com/tv"); //tv프로그램
//            System.out.println("tv프로그램 = " + driver.getCurrentUrl());
//
//            driver.navigate().to("https://www.tving.com/list/program?genre=PCA"); //tv프로그램>드라마
//            System.out.println("tv프로그램>드라마 = " + driver.getCurrentUrl());
//
//            //무한 스크롤링 대상 요소
//            WebElement item = driver.findElement(By.className("item"));
//
//            var stTime = new Date().getTime(); //현재시간
//            while (new Date().getTime() < stTime + 30000) { //30초 동안 무한스크롤 지속
//                Thread.sleep(500); //리소스 초과 방지
//                //executeScript: 해당 페이지에 JavaScript 명령을 보내는 거
//                ((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)", item);
//            }
//        } finally {
//            driver.quit();
//            driver1.quit();
//        }
//    }
//}
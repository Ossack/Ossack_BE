
//package com.sparta.startup_be.utils;



//package com.sparta.startup_be.utils;
//

//import com.sparta.startup_be.dto.EstateDto;
//import com.sparta.startup_be.model.Estate;
//import lombok.extern.slf4j.Slf4j;
//import org.openqa.selenium.*;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.interactions.Actions;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//@Slf4j
//@Service
//public class WebDriverUtil {
//    private WebDriver driver;
//    public static String WEB_DRIVER_ID = "webdriver.chrome.driver"; // Properties 설정
//    public static String WEB_DRIVER_PATH = "C:/Users/장윤희/Desktop/미누/chromedriver.exe"; // WebDriver 경로
//
//
//
//    public WebDriverUtil() {
//        chrome();
//    }
//
//    private void chrome() {
//        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
//
//        // webDriver 옵션 설정.
//        ChromeOptions options = new ChromeOptions();
//        options.setHeadless(true);
//        options.addArguments("--lang=ko");
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--disable-gpu");
//        options.setCapability("ignoreProtectedModeSettings", true);
//        // weDriver 생성.
//        driver = new ChromeDriver();
//        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
//    }
//
//    public List<Estate> useDriver(String url) throws InterruptedException {
//        Actions action = new Actions(driver);
//        driver.get(url) ;
//        driver.manage().window().maximize();
//        driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);  // 페이지 불러오는 여유시간.
//        log.info("++++++++++++++++++++++===================+++++++++++++ selenium : " + driver.getTitle());
//        try{
//            driver.findElement(By.className("btn_option")).click();
//        }catch(Exception e) {
//            e.printStackTrace();
//        }
//        WebElement item = driver.findElement(By.className("article_box"));
//        int m = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"_listContainer\"]/div/div[1]/a/h3/strong")).getText().replace("+",""));
//        int j=0;
//        while(true) {
//            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(0, document.body.scrollHeight)", item);
//            Thread.sleep(1);
//            j++;
//            if(j==m) break;
//        }
//        List<Estate> estates = new ArrayList<>();
//
//        List<WebElement> webElements = driver.findElements(By.className("item_area"));
////        System.out.println(webElements.size());
//        int i=0;
//        for(WebElement webElement : webElements){
//            Thread.sleep(3000);
//            if(!webElement.findElement(By.className("merit_area")).getText().contains("중개사")&&!webElement.findElement(By.className("agent_name")).getText().contains("피터팬")){
//                webElement.findElement(By.className("item_link")).sendKeys(Keys.CONTROL +"\n");
//                driver.switchTo().window((String) driver.getWindowHandles().toArray()[1]);
//                driver.switchTo().frame(driver.findElement(By.id("_newMobile")));
//                Thread.sleep(1000);
//
//                String type =driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div[1]/div/div[1]/div[2]/strong")).getText();
//                String a = driver.findElement(By.xpath("//*[@id=\"detailMy--fixed\"]/strong")).getText();
//                String monthly = driver.findElement(By.xpath("//*[@id=\"detailMy--fixed\"]/em")).getText();
////                System.out.println(type+" "+a+" "+monthly);
//                int rent_fee=0;
//                if(monthly.equals("단기임대")||monthly.equals("월세")) {
//                    rent_fee = Integer.parseInt(a.split("\n")[2].replace(" ","").replace("/","").replace(",",""));
//                }
////                List<String> subwayString = new ArrayList<>();
////                List<WebElement> subwayElements =driver.findElements(By.className("detail_facilities_item "));
////                System.out.println(subwayElements.size());
////                for(WebElement subway: subwayElements){
////                    System.out.println("왜 안대냐");
////                    System.out.println(subway.getText());
////                    subwayString.add(subway.getText());
////                }
//                String deposit = a.split("\n")[0];
//                List<WebElement> images = driver.findElements(By.className("detail_photo_item"));
//                List<String> imageList = new ArrayList<>();
//                for(WebElement image : images){
//                    String imageUrl = image.getAttribute("style").split("\"")[1];
//                    imageList.add(imageUrl);
//                }
//                String info = driver.findElement(By.className("detail_summary_item")).getText();
//                String area = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div[2]/div[2]/div[1]/div/span[2]")).getText().split("\n")[0];
//                int buildingFloor =0;
//                int roomFloor=0;
//                Long id = 0L;
//                try {
//                    buildingFloor = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div[2]/div[2]/div[3]/div[2]/span[2]"))
//                            .getText().split("/")[1].replace("층",""));      //*[@id="content"]/div/div[1]/div[2]/div[2]/div[4]/div[2]/span[2]
//                    roomFloor = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div[2]/div[2]/div[3]/div[2]/span[2]"))
//                            .getText().split("/")[0].replace("B",""));
//                    id = Long.parseLong(driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div[2]/div[2]/div[11]/div/span[2]")).getText());
//                }catch (IndexOutOfBoundsException e){
//                    buildingFloor = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div[2]/div[2]/div[4]/div[2]/span[2]"))
//                            .getText().split("/")[1].replace("층",""));      //*[@id="content"]/div/div[1]/div[2]/div[2]/div[4]/div[2]/span[2]
//                    roomFloor = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div[2]/div[2]/div[4]/div[2]/span[2]"))
//                            .getText().split("/")[0].replace("B",""));
//                    id = Long.parseLong(driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div[2]/div[2]/div[12]/div/span[2]")).getText());
//                }
//                String city = driver.findElement(By.className("detail_info_branch")).getText();
//                i++;
//                System.out.println("i="+i);
//                EstateDto estateDto = EstateDto.builder()
//                        .id(id).area(area).buildingFloor(buildingFloor).roomFloor(roomFloor).imageList(imageList)
//                        .deposit(deposit).city(city).rent_fee(rent_fee).type(type).buildingInfo(info).monthly(monthly)
//                        .build();
//                Estate estate =new Estate(estateDto);
//                estates.add(estate);
//
//                driver.close();
//                driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());
//
////                System.out.println(driver.findElement(By.cssSelector("iframe")).getAttribute("id"));
//
//            }
//
//        }
//        log.info("++++++++++++++++++++++===================+++++++++++++ 끝 : " );
//
//        quitDriver();
//        return estates;
//    }
//
//    private void quitDriver() {
//        driver.quit(); // webDriver 종료
//    }
//
//}
//
//


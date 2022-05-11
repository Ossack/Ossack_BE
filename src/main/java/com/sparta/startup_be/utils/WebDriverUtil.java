
package com.sparta.startup_be.utils;

import com.sparta.startup_be.dto.EstateRequestDto;
import com.sparta.startup_be.model.Estate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebDriverUtil extends Thread {
    private WebDriver driver;
    private List<Estate> result;
    public static String WEB_DRIVER_ID = "webdriver.chrome.driver"; // Properties 설정
    public static String WEB_DRIVER_PATH = "chromedriver"; // WebDriver 경로
    //C:/Users/장윤희/Desktop/미누/chromedriver.exe
    private int num;
    @Override
    public void run(){
        try {
            useDriverNemo();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public WebDriverUtil(int num) {
        chrome();
        this.num = num;
    }

    public List<Estate> getResult(){
        return result;
    }

    private void chrome() {
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        // webDriver 옵션 설정.
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--lang=ko");
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("disable-gpu");
//        options.addArguments("window-size=1920,1080");
        options.setCapability("ignoreProtectedModeSettings", true);
        // weDriver 생성.
        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
    }

    public List<Estate> useDriver(String url) throws InterruptedException {
        driver.get(url);
//        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);  // 페이지 불러오는 여유시간.
        log.info("++++++++++++++++++++++===================+++++++++++++ selenium : " + driver.getTitle());
        try {
            driver.findElement(By.className("btn_option")).sendKeys(Keys.ENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        WebElement item = driver.findElement(By.className("article_box"));
        int m = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"_listContainer\"]/div/div[1]/a/h3/strong")).getText().replace("+", ""));
        int j = 0;
        while (true) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(0, document.body.scrollHeight)", item);
            Thread.sleep(1);
            j++;
            if (j == 500) break;
        }
        List<Estate> estates = new ArrayList<>();

        List<WebElement> webElements = driver.findElements(By.className("item_area"));

        System.out.println(webElements.size());
        int i = 0;
        try {
            for (WebElement webElement : webElements) {
                i++;
                System.out.println("i=" + i);
                if (i % 5 == 0) {
                    Thread.sleep(500);
                    if (!webElement.findElement(By.className("merit_area")).getText().contains("중개사") && !webElement.findElement(By.className("agent_name")).getText().contains("피터팬")) {
                        webElement.findElement(By.className("item_link")).sendKeys(Keys.CONTROL + "\n");
                        driver.switchTo().window((String) driver.getWindowHandles().toArray()[1]);
                        driver.switchTo().frame(driver.findElement(By.id("_newMobile")));
                        Thread.sleep(1000);

                        String type = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div[1]/div/div[1]/div[2]/strong")).getText();
                        String a = driver.findElement(By.xpath("//*[@id=\"detailMy--fixed\"]/strong")).getText();
                        String monthly = driver.findElement(By.xpath("//*[@id=\"detailMy--fixed\"]/em")).getText();
//                System.out.println(type+" "+a+" "+monthly);
                        String rent_fee = "";
                        if (monthly.equals("단기임대") || monthly.equals("월세")) {
                            rent_fee = a.split("\n")[2].replace(" ", "").replace("/", "").replace(",", "");
                        }

//                List<String> subwayString = new ArrayList<>();
//                List<WebElement> subwayElements =driver.findElements(By.className("detail_facilities_item "));
//                System.out.println(subwayElements.size());
//                for(WebElement subway: subwayElements){
//                    System.out.println("왜 안대냐");
//                    System.out.println(subway.getText());
//                    subwayString.add(subway.getText());
//                }
                        String deposit = a.split("\n")[0];
                        List<WebElement> images = driver.findElements(By.className("detail_photo_item"));
                        List<String> imageList = new ArrayList<>();
                        for (WebElement image : images) {
                            String imageUrl = image.getAttribute("style").split("\"")[1];
                            imageList.add(imageUrl);
                        }
                        String info = driver.findElement(By.className("detail_summary_item")).getText();
                        String area = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div[2]/div[2]/div[1]/div/span[2]")).getText().split("\n")[1];
                        String buildingFloor = "";
                        String roomFloor = "";
                        Long id = 0L;
                        try {
                            buildingFloor = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div[2]/div[2]/div[3]/div[2]/span[2]"))
                                    .getText().split("/")[1].replace("층", "");      //*[@id="content"]/div/div[1]/div[2]/div[2]/div[4]/div[2]/span[2]
                            roomFloor = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div[2]/div[2]/div[3]/div[2]/span[2]"))
                                    .getText().split("/")[0].replace("B", "");
                            id = Long.parseLong(driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div[2]/div[2]/div[11]/div/span[2]")).getText());
                        } catch (IndexOutOfBoundsException e) {
                            buildingFloor = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div[2]/div[2]/div[4]/div[2]/span[2]"))
                                    .getText().split("/")[1].replace("층", "");      //*[@id="content"]/div/div[1]/div[2]/div[2]/div[4]/div[2]/span[2]
                            roomFloor = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div[2]/div[2]/div[4]/div[2]/span[2]"))
                                    .getText().split("/")[0].replace("B", "");
                            id = Long.parseLong(driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div[2]/div[2]/div[12]/div/span[2]")).getText());
                        }
                        String city = driver.findElement(By.className("detail_info_branch")).getText();
                        i++;
                        System.out.println("i=" + i);
                        EstateRequestDto estateDto = EstateRequestDto.builder()
                                .id(id).area(area).buildingFloor(buildingFloor).roomFloor(roomFloor).imageList(imageList)
                                .deposit(deposit).city(city).rent_fee(rent_fee).type(type).buildingInfo(info).monthly(monthly)
                                .build();
                        Estate estate = new Estate(estateDto);
                        estates.add(estate);

                        driver.quit();

//                System.out.println(driver.findElement(By.cssSelector("iframe")).getAttribute("id"));

                    }

                }
            }
        }catch(Exception e){
            System.out.println("i+"+e.toString());
        }
        log.info("++++++++++++++++++++++===================+++++++++++++ 끝 : ");

        quitDriver();
        return estates;
    }


    public List<Estate> useDriverNemo() throws InterruptedException {
        driver.get("https://www.nemoapp.kr/Search?ArticleType=2&PageIndex=0&StoreTrade=false&CompletedOnly=false&SWLng=126.05317076864321&SWLat=37.108502652287&NELng=128.2605043452823&NELat=37.95434466928463&Zoom=10&mode=1&category=1&list=true&articleId=&dataType=");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);  // 페이지 불러오는 여유시간.
        log.info("++++++++++++++++++++++===================+++++++++++++ selenium : " + driver.getTitle());
//        try {
//            driver.findElement(By.className("nav_count")).click();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        Thread.sleep(1000);
        WebElement item = driver.findElement(By.className("list_area"));
        int m = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"searchPlaceHolder\"]/div/div[3]/div[1]/ul/li[1]/span/em")).getText().replace(",", ""));
        int j = 0;
        Thread.sleep(1000);
        do {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(0, document.body.scrollHeight)", item);
            Thread.sleep(10);
            j++;
        } while (j != 50);

        List<WebElement> webElements = driver.findElements(By.className("article_row"));
        System.out.println(webElements.size());
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo(0, 100)", item);

        List<Estate> estates= new ArrayList<>();
        int i = 0;
        for (WebElement webElement : webElements) {
//            try {
                if (i % 8 == num) {
                    String monthly = webElement.findElement(By.className("primary")).findElement(By.className("type")).getText();
                    Thread.sleep(1000);
                    webElement.sendKeys(Keys.ENTER);


                    String a = driver.findElement(By.className("detail_price")).getText();
                    String deposit = a.split("/")[0].replace("보증금", "");
                    String subwayInfo = webElement.findElement(By.xpath("//*[@id=\"summary\"]/div[2]/div[3]/p[1]")).getText();
                    String rent_fee = "0";
                    if (monthly.equals("월세")) {
                        rent_fee = a.split("/")[1].replace("\n", "").replace("월세", "");
                    }
//                List<String> subwayString = new ArrayList<>();
//                List<WebElement> subwayElements =driver.findElements(By.className("detail_facilities_item "));
//                System.out.println(subwayElements.size());
//                for(WebElement subway: subwayElements){
//                    System.out.println("왜 안대냐");
//                    System.out.println(subway.getText());
//                    subwayString.add(subway.getText());
//                }
                    List<WebElement> images = driver.findElement(By.className("product_thumb_area")).findElements(By.className("swiper-slide"));
                    List<String> imageList = new ArrayList<>();
                    for (WebElement image : images) {
                        String imageUrl = image.getAttribute("style").split("\"")[1];
                        imageList.add(imageUrl);
                    }
                    Long id = Long.parseLong(driver.findElement(By.className("product_number")).getText().split(" ")[1]);
                    String info = driver.findElement(By.className("detail_title")).getText();
                    WebElement scroll = driver.findElement(By.className("content_area"));

                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(0, 200)", scroll);
                    String area = driver.findElement(By.className("area")).getText().split("\n")[1];
//                    System.out.println(area);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(0, 200)", scroll);

//            buildingFloor = Integer.parseInt(driver.findElement(By.className("floor")).getText()
//                    .split("/")[1].replace("층", ""));      //*[@id="content"]/div/div[1]/div[2]/div[2]/div[4]/div[2]/span[2]
//            roomFloor =  Integer.parseInt(driver.findElement(By.className("floor")).findElement(By.className("content")).getText()
////                    .split("/")[0].replace("층", ""));


                    String[] product = driver.findElement(By.className("product_more")).getText().split("\n");
                    String buildingFloor = "";
                    String roomFloor = "";
//            System.out.println(product.split("\n"));
                    for (String s : product) {
                        if (s.contains("층")) {
                            buildingFloor = s.split("/")[1].replace(" ", "").split("층")[0];
                            roomFloor = s.split("/")[0].replace(" ", "");
                            break;
                        }
                    }

                    String type = product[0];
//            String floor = product.split("\n")[3];
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(0, 400)", scroll);

//                    driver.findElement(By.xpath("//*[@id=\"article-information\"]/div[2]/div[2]/div[2]/button")).sendKeys(Keys.ENTER);
                    String buidlingDetail = webElement.findElement(By.xpath("//*[@id=\"article-information\"]/div[2]/div[2]/div[1]/div[1]")).getText();

                    String city = "";
                    String agent ="";
                    do {
                        city = driver.findElement(By.className("data_position")).getText();
                        agent = driver.findElement(By.className("agent_name")).getText();
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(0, 100)", scroll);
                    } while (agent.replace(" ","").equals("") && city.replace(" ","").equals(""));
//                    System.out.println("city: "+city);
//                    System.out.println(agent);
//                    city = city.split("\n")[1];
                    System.out.println("i=" + i);
//            System.out.println("id="+id);
//            System.out.println("area="+area);
//            System.out.println("rent_fee="+rent_fee);
//            System.out.println("deposit="+deposit);
//            System.out.println("buildingFloor="+buildingFloor);
//            System.out.println("info="+info);
//            System.out.println("roomFloor="+roomFloor);
//            System.out.println("id="+id);
//            System.out.println("floor="+floor);
//            System.out.println("imageList="+imageList);
//                    System.out.println("city=" + city);
//            System.out.println("type="+type);
                    EstateRequestDto estateDto = EstateRequestDto.builder()
                            .id(id).area(area).buildingFloor(buildingFloor).roomFloor(roomFloor).imageList(imageList).subwayInfo(subwayInfo).buildingDetail(buidlingDetail)
                            .deposit(deposit).city(city).rent_fee(rent_fee).type(type).buildingInfo(info).monthly(monthly).office("사무실").agent(agent)
                            .build();
                    Estate estate = new Estate(estateDto);
                    estates.add(estate);


                    driver.findElement(By.className("btn_prev")).click();
//                System.out.println(driver.findElement(By.cssSelector("iframe")).getAttribute("id"));

                }
//            }catch(Exception e){
//                System.out.println("i번째에서"+e+"발생");
//            }
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(0, 150)", item);
            i++;
        }
        log.info("++++++++++++++++++++++===================+++++++++++++ 끝 : ");
        result=estates;
        quitDriver();
        return estates;
    }

    private void quitDriver() {
        driver.quit(); // webDriver 종료
    }
}






package com.sparta.startup_be.utils;

import com.sparta.startup_be.estate.dto.EstateRequestDto;
import com.sparta.startup_be.sharedOffice.dto.SharedOfficeDto;
import com.sparta.startup_be.model.Estate;
import com.sparta.startup_be.model.SharedOffice;
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
    public static String WEB_DRIVER_PATH = "C:/Users/장윤희/Desktop/미누/chromedriver.exe"; // WebDriver 경로
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
        driver = new ChromeDriver();
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
        driver.get("https://www.nemoapp.kr/Search?ArticleType=2&PageIndex=0&StoreTrade=false&CompletedOnly=false&SWLng=126.96048262132093&SWLat=37.46440243051975&NELng=127.1342296678897&NELat=37.53360211645057&Zoom=14&mode=1&category=1&list=true&articleId=&dataType=  ");
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
            System.out.println(j);
        } while (j != 1000);


        List<WebElement> webElements = driver.findElements(By.className("article_row"));
        System.out.println(webElements.size());
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo(0, 0)", item);

        List<Estate> estates= new ArrayList<>();
        int i = 0;
        for (WebElement webElement : webElements) {
//            try {
                if (i % 8 == num) {

                    String monthly = webElement.findElement(By.className("primary")).findElement(By.className("type")).getText();
                    webElement.sendKeys(Keys.ENTER);
                    Thread.sleep(1000);

                    //스크롤 선언
                    WebElement scroll = driver.findElement(By.className("content_area"));

                    //이미지 크롤링
                    List<WebElement> images = driver.findElement(By.className("product_thumb_area")).findElements(By.className("swiper-slide"));
                    List<String> imageList = new ArrayList<>();
                    for (WebElement image : images) {
                        String imageUrl = image.getAttribute("style").split("\"")[1];
                        imageList.add(imageUrl);
                    }

                    //보증금 월세 크롤링
                    String a = driver.findElement(By.className("detail_price")).getText();
                    String deposit = a.split("/")[0].replace("보증금", "");
                    String rent_fee = "0";
                    if (monthly.equals("월세")) {
                        rent_fee = a.split("/")[1].replace("\n", "").replace("월세", "");
                    }


                    //지하철 정보 크롤링
                    String subwayInfo = webElement.findElement(By.xpath("//*[@id=\"summary\"]/div[2]/div[3]/p[1]")).getText();
                    //매물 번호 크롤링
                    Long id = Long.parseLong(driver.findElement(By.className("product_number")).getText().split(" ")[1]);
                    //매물 간단 정보 크롤링
                    String info = driver.findElement(By.className("detail_title")).getText();



                    //전용면적 크롤링
                    String area = "";
                    String area_1 ="";
                    while(true){
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(0, 50)", scroll);
                        area_1 = driver.findElement(By.className("area")).getText();
                        if(area_1.contains("㎡")) break;
                    }
                    area = area_1.split("\n")[1];
                    String capacity = driver.findElement(By.className("data_point")).findElement(By.className("person")).findElement(By.className("content")).getText();
                    String management_fee = driver.findElement(By.className("data_point")).findElement(By.className("management")).findElement(By.className("content")).getText();
                    System.out.println(i);


                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(0, 400)", scroll);

                    //정보 list 크롤링(엘리베이터 유무, 입주 가능일, 층수)
                    List<WebElement> products = driver.findElement(By.className("product_more")).findElements(By.tagName("li"));
                    String elevator ="개별문의";
                    String date = "개별문의";
                    String buildingFloor ="개별문의";
                    String roomFloor ="개별문의";
                    String type = products.get(0).getText();
                    String toilet = "개별문의";
                    String parking = "개별문의";
                    for(WebElement product : products){
                        System.out.println(product.getText());
                        if(product.getText().contains("엘리베이터")){
                            elevator=product.getText();
                        }else if(product.getText().contains("즉시")){
                            date = product.getText();
                        }else if(product.getText().contains("층")){
                            buildingFloor = product.getText().split("/")[1].replace("층","");
                            roomFloor = product.getText().split("/")[0];
                        }else if(product.getText().contains("녀")){
                            toilet = product.getText();
                        }else if(product.getText().contains("주차")){
                            parking=product.getText();
                        }
                    }
                    System.out.println(parking);


                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(0, 500)", scroll);

                    //빌딩 설명 크롤링
                    String buidlingDetail = webElement.findElement(By.xpath("//*[@id=\"article-information\"]/div[2]/div[2]/div[1]/div[1]")).getText();

                    //주소 및 중개사 크롤링
                    String city = "개별문의";
                    String agent ="개별문의";
                    do {
                        city = driver.findElement(By.className("data_position")).getText();
                        agent = driver.findElement(By.className("agent_name")).getText();
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(0, 100)", scroll);
                    } while (agent.replace(" ","").equals("") || city.replace(" ","").equals(""));

                    WebElement button = driver.findElement(By.className("btn_contact"));
                    button.click();
                    String agentInfo = driver.findElement(By.className("popup")).findElement(By.className("agent_name")).getText();
                    String personIncharge = agentInfo.split("\n")[1].split(" ")[1];
                    String phoneNumber = agentInfo.split("\n")[1].split(" ")[2];
                    String agentNumber = driver.findElement(By.className("agent_telephone")).getText().split("·")[0];

                    driver.findElement(By.className("btn_close")).click();


                    EstateRequestDto estateDto = EstateRequestDto.builder()
                            .id(id).area(area).buildingFloor(buildingFloor).roomFloor(roomFloor).imageList(imageList).subwayInfo(subwayInfo).parking(parking)
                            .buildingDetail(buidlingDetail).elevator(elevator).date(date).toilet(toilet).management_fee(management_fee).capacity(capacity)
                            .deposit(deposit).city(city).rent_fee(rent_fee).type(type).buildingInfo(info).monthly(monthly).office("사무실").agent(agent)
                            .agentNumber(agentNumber).phoneNumber(phoneNumber).personIncharge(personIncharge)
                            .build();
                    Estate estate = new Estate(estateDto);
                    estates.add(estate);


                    driver.findElement(By.className("btn_prev")).click();
                }
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

    public List<SharedOffice> crawlingSharedOffice() throws InterruptedException {
        driver.get("https://www.nemoapp.kr/Search?PageIndex=0&SWLng=126.4535483683688&SWLat=37.3233896658737&NELng=127.5658939958555&NELat=37.753466376953554&Zoom=11&category=2&list=true&branchId=&dataType=");
        driver.manage().timeouts().implicitlyWait(20000, TimeUnit.MILLISECONDS);  // 페이지 불러오는 여유시간.
        driver.manage().window().maximize();
        log.info("++++++++++++++++++++++===================+++++++++++++ selenium : " + driver.getTitle());
//        try {
//            driver.findElement(By.className("nav_count")).click();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        Thread.sleep(1000);
        WebElement item = driver.findElement(By.className("list_area"));
        int j = 0;
        do {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(0, document.body.scrollHeight)", item);
            Thread.sleep(10);
            j++;
            System.out.println(j);
        } while (j != 10);


        List<WebElement> webElements = driver.findElements(By.className("coworking_card"));
        System.out.println(webElements.size());
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollTo(0, 0)", item);



        List<SharedOffice> sharedOffices =new ArrayList<>();
        int i = 0;
        for (WebElement webElement : webElements) {
//            try {
            if (i % 1 == num) {
                webElement.click();
                //스크롤 선언
                WebElement scroll = driver.findElement(By.className("content_area"));

                //이미지 크롤링
                List<WebElement> images = driver.findElement(By.className("product_thumb_area")).findElements(By.className("swiper-slide"));
                List<String> imageList = new ArrayList<>(); //imageList선언
                List<String> coveniences = new ArrayList<>(); //편의시설 List 선언
                for (WebElement image : images) {
                    String imageUrl = image.getAttribute("style").split("\"")[1];
                    imageList.add(imageUrl);
                }

                //건물이름 크롤링
                String name = driver.findElement(By.className("detail_name")).getText();
//                System.out.println(name);

                //지하철 정보 크롤링
                String subwayInfo = driver.findElement(By.className("detail_address")).getText();
//                System.out.println(subwayInfo);

                //비용크롤링
                String price = driver.findElement(By.className("detail_price")).getText();
//                System.out.println(price);

                //이용시간 크롤링
                String time = driver.findElement(By.className("product_data")).findElement(By.className("time")).findElement(By.className("content")).getText();
//                System.out.println(time);

                //최소이용기간 크롤링
                String minimum_days = driver.findElement(By.className("minimum_days")).findElement(By.className("content")).getText();
//                System.out.println(minimum_days);

                //층수 크롤링
                String floor = driver.findElement(By.className("floor")).findElement(By.className("content")).getText();
//                System.out.println(floor);

                //주차비 크롤링
                String parking = driver.findElement(By.className("parking")).findElement(By.className("content")).getText();
//                System.out.println(parking);


                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(0, 400)", scroll);

                //상세설명크롤링
                String detail ="";
                while(true){
                    detail=driver.findElement(By.className("description")).getText();
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(0, 100)", scroll);
                    if(!detail.replace(" ","").replace("\n","").equals("")) break;
                }

                //편의시설 크롤링
                List<WebElement> covenienceList = new ArrayList<>();
                try {
                    while (true) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(0, 100)", scroll);
                        covenienceList = driver.findElement(By.className("covenience_list")).findElements(By.tagName("li"));
                        if (!covenienceList.get(covenienceList.size()-1).getText().replace(" ","").replace("\n","").equals("")) break;
                    }
                }catch(NoSuchElementException e){
                    e.printStackTrace();
                    covenienceList = new ArrayList<>();
                }
                System.out.println(covenienceList.size());
                for(WebElement covenience : covenienceList){
//                    System.out.println(covenience.getText());
                    coveniences.add(covenience.getText());
                }


                String address = "개별문의";
                while (true) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(0, 100)", scroll);
                    address = driver.findElement(By.className("data_position")).getText();
                    if (!address.replace(" ","").replace("\n","").equals("")) break;
                }
//                System.out.println(address);

//                System.out.println(driver.findElement(By.tagName("tr")).getText());
//                List<WebElement> webElements1 = driver.findElements(By.className("tbl_option"));
//                System.out.println(webElements1.size());
//
//                for(int k = 0 ; k<webElements1.size(); k++){
//                    String text = webElements1.get(k).findElement(By.tagName("tr")).getText();
//                    System.out.println(text);
//                }
                try {
                    driver.findElement(By.className("btn_contact")).click();
                    //전화번호 크롤링
                    String number = driver.findElement(By.className("agent_telephone")).getText().replace(" ","").replace("대표번호","");
//                System.out.println(number);
                    //공인중개사
                    String agentInfo = driver.findElement(By.className("agent_address")).getText();
                    String agent = agentInfo.split("[(]")[0].split(" ","");
//                System.out.println(agent);
                    String personIncharge = agentInfo.split("[(]")[1].replace("(","").replace(")","").replace(" ","").replace("대표","");
//                System.out.println(personIncharge);
                    driver.findElement(By.className("btn_close")).click();

                    SharedOfficeDto sharedOfficeDto = SharedOfficeDto.builder()
                            .detail(detail)
                            .floor(floor)
                            .imageList(imageList)
                            .minimum_days(minimum_days)
                            .convience(coveniences)
                            .agent(agent)
                            .number(number)
                            .city(address.split(" ")[0])
                            .gu(address.split(" ")[1])
                            .dong(address.split(" ")[2])
                            .personIncharge(personIncharge)
                            .name(name)
                            .parking(parking)
                            .subwayInfo(subwayInfo)
                            .price(price)
                            .time(time)
                            .build();
                    SharedOffice sharedOffice =new SharedOffice(sharedOfficeDto);
                    sharedOffices.add(sharedOffice);
                }catch(NoSuchElementException e){
                    e.printStackTrace();
                }
                driver.findElement(By.className("btn_prev")).click();
            }
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(0, 314)", item);
            i++;
        }
        log.info("++++++++++++++++++++++===================+++++++++++++ 끝 : ");
        quitDriver();
        return sharedOffices;
    }
}





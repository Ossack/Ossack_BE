
package com.sparta.startup_be.utils;


import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NemoApi {

    public String nemoApi(){
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("https://www.nemoapp.kr/api/articles/search/?Radius=&Latitude=&Longitude=&Building=&PageSize=&SortBy=&DepositMin=&DepositMax=&MRentMin=&MRentMax=&SaleMin=&SaleMax=&Premium=&PremiumMin=&PremiumMax=&DealType=&ArticleType=1&BuildingType=&PriceType=&SizeMin=&SizeMax=&MFeeMin=&MFeeMax=&Floor=&IsAllFloors=&Parking=&ParkingSlotMin=&ParkingSlotMax=&Interior=&Elevator=&IndependentSpaceCount=&Toilet=&BYearMin=&BYearMax=&RoofTop=&Terrace=&PantryRoom=&AirConditioner=&VR=&OfficeShare=&ShopInShop=&OpenLateNight=&Remodeling=&AddSpaceOffer=&BusinessField=&IsExclusive=&AgentId=&UserId=&PageIndex=0&Region=&Subway=&StoreTrade=&CompletedOnly=&LBiz=&MBiz=&InitialExpMin=&InitialExpMax=&IsCommercialDistrictUnknown=&IsCommercialDistrictSubway=&IsCommercialDistrictUniversity=&IsCommercialDistrictOffice=&IsCommercialDistrictResidential=&IsCommercialDistrictDowntown=&IsCommercialDistrictSuburbs=&MoveInDate=&HeatingType=&SWLng=126.88140169467066&SWLat=37.52223019169641&NELng=126.93463080473451&NELat=37.55575280718502&Zoom=15", HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        System.out.println("Response status: " + status);
        System.out.println(response);
        return response;
    }
}

package com.sparta.startup_be.service;

import com.sparta.startup_be.model.Estate;
import com.sparta.startup_be.model.Favorite;
import com.sparta.startup_be.repository.EstateRepository;
import com.sparta.startup_be.repository.FavoriteRepository;
import com.sparta.startup_be.login.repository.UserRepository;
import com.sparta.startup_be.security.UserDetailsImpl;
import com.sparta.startup_be.login.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.sparta.startup_be.dto.*;
import com.sparta.startup_be.repository.CoordinateRepository;
import com.sparta.startup_be.utils.ConvertAddress;

import java.util.*;


@RequiredArgsConstructor
@Service
public class EstateService {
    private final EstateRepository estateRepository;
    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;
    private final CoordinateRepository coordinateRepository;
    private final ConvertAddress convertAddress;


//    public List<Estate> show(){
//        return estateRepository.findAllByFloor(4);
//    }

    public void storeEstate(List<Estate> estates) {
        for (Estate estate : estates) {
            estateRepository.save(estate);
        }
    }

    //지역검색하기
    public String average(String query) {
        List<Estate> estates = estateRepository.findAllByCity(query);
        double sum_fee = 0;
        double sum_deposit = 0;
        for (Estate estate : estates) {
            if (estate.getMonthly().equals("월세")) {
//                sum_fee += estate.getRent_fee()/estate.getArea();
            }
//            else{
//                sum_deposit += Integer.parseInt(estate.getDeposit().replace(",","")) / estate.getArea();
//            }
        }
        double avg_fee = sum_fee / estateRepository.countAllByMonthlyAndCity("월세", query) * 3.3;
        double avg_deposit = sum_deposit / estateRepository.countAllByMonthlyAndCity("전세", query) * 3.3;
        return "월세 평균은" + avg_fee + "이고, 전세보증금 평균은" + avg_deposit + "입니다.";
    }

    //메인페이지 해당 동 조회
    public List<EstateResponseDto> searchKeyword(String query, UserDetailsImpl userDetails) {
        List<EstateResponseDto> estateResponseDtoList = new ArrayList<>();
        System.out.println(query);
        String keyword = "";
        if (query.equals("맛집")) {
            keyword = "서울특별시 서초구 양재동";
        } else if (query.equals("역")) {
            keyword = "서울특별시 서초구 서초동";
        }
        System.out.println("잘들오네요");
        List<Estate> estates = estateRepository.searchAllByCity(keyword);
        int i = 0;
        for (Estate estate : estates) {
            boolean mylike = favoriteRepository.existsByEstateidAndUserid(estate.getId(), userDetails.getId());
            EstateResponseDto estateResponseDto = new EstateResponseDto(estate, mylike);
            estateResponseDtoList.add(estateResponseDto);
            i++;
            if (i == 4) break;
        }

        return estateResponseDtoList;
    }

    //리스트 선택후 조회
    public EstateResponseDto showDetail(Long estateid, User user){
        Estate estate = estateRepository.findById(estateid).orElseThrow(
                () -> new IllegalArgumentException("사라진 매물입니다")
        );
        boolean mylike = favoriteRepository.existsByEstateidAndUserid(estateid,user.getId());
        return new EstateResponseDto(estate,mylike);
    }


    public List<EstateResponseDto> searchTowm(String query, UserDetailsImpl userDetails) {
        List<EstateResponseDto> estateResponseDtoList = new ArrayList<>();
        List<Estate> estates = estateRepository.searchAllByCity(query);
        int i = 0;
        for (Estate estate : estates) {
            boolean mylike = favoriteRepository.existsByEstateidAndUserid(estate.getId(), userDetails.getId());
            EstateResponseDto estateResponseDto = new EstateResponseDto(estate, mylike);
            estateResponseDtoList.add(estateResponseDto);
            i++;
            if (i == 4) break;
        }

        return estateResponseDtoList;
    }

    //핫한 매물 보기기
    public List<Map<String, Object>> searchHot(UserDetailsImpl userDetails) {
        //        for(Map<String,Object> asdd : asd){
//            boolean mylike = favoriteRepository.existsByEstateidAndUserid(Long.valueOf(String.valueOf(asdd.get("id"))),userDetails.getId());
//            asdd.put("mylike",mylike);
//        }
        return favoriteRepository.countUseridQuery();
    }

    //구별로 모아보기
    public List<Estate> guAverage(String query) {
        //        for(Estate estate : estates){
//            System.out.println(estate.getCity());
//        }
        return estateRepository.searchAllByCity(query);
    }


    // 찜한것 보기
    public List<EstateResponseDto> showFavorite(UserDetailsImpl userDetails) {

        // 찜한 매물 목록
        List<Favorite> favoriteList = favoriteRepository.findByUserid(userDetails.getId());

        List<EstateResponseDto> estateResponseDtos = new ArrayList<>();
        for (int i = 0; i < favoriteList.size(); i++) {
            favoriteList.get(i).getEstateid();
//            System.out.println(favoriteList.get(i).getEstateid());
            Estate estate = estateRepository.findById(favoriteList.get(i).getEstateid()).orElseThrow(
                    () -> new NullPointerException("게시글이 없습니다"));
            EstateResponseDto estateResponseDto = new EstateResponseDto(estate, true);
            estateResponseDtos.add(estateResponseDto);
        }
        return estateResponseDtos;
    }



    public MapResponseDto showEstate(float minX, float maxX, float minY, float maxY, int level, UserDetailsImpl userDetails) {
        List<String> cities = estateRepository.findCity(minX,maxX,minY,maxY);
//        List<Coordinate> coordinates = coordinateRepository.findAllByXBetweenAndYBetween(minX, maxX, minY, maxY);
////        List<Coordinate> coordinates = coordinateRepository.findAllByXBetween(minX,maxX);
////        System.out.println(coordinates.size());
//        long temp1 = System.currentTimeMillis();
//        System.out.println(temp1 - start);
//        Set<String> cities = new HashSet<>();
//        for (Coordinate coordinate : coordinates) {
//            Estate estate2 = estateRepository.findById(coordinate.getEstateid()).orElseThrow(
//                    () -> new IllegalArgumentException("하이")
//            );
//            String city = "";
//            if (level < 7) {
//                city = estate2.getCity();
//            } else if (level == 7 || level == 8) {
//                city = estate2.getCity().split(" ")[0] + " " + estate2.getCity().split(" ")[1];
//            } else {
//                city = estate2.getCity().split(" ")[0];
//            }
//            cities.add(city);
//        }

        long start =System.currentTimeMillis();
        System.out.println(cities.size());
        List<String> cities2 = new ArrayList<>();

        long end =System.currentTimeMillis();
        System.out.println(end-start);
        System.out.println("size"+cities2.size());
//        Iterator<String> it = cities.iterator();
        List<CityResponseDto> cityResponseDtoList = new ArrayList<>();
        for(int i=0; i<cities2.size(); i++) {
            String title = cities2.get(i);
            System.out.println(title);
            List<EstateResponseDto> estate = new ArrayList<>();
            List<Estate> estateList =new ArrayList<>();
            float avg = 0f;
            if (level < 7) {
                 estateList = estateRepository.searchAllBydong(title);
                 avg  = estateRepository.dongAvgQuery(title);
            } else if (level == 7 || level == 8) {
                 estateList = estateRepository.searchAllBygu(title);
                 avg = estateRepository.guAvgQuery(title);
            } else {
                 estateList = estateRepository.searchAllByCity(title);
                 avg = estateRepository.cityAvgQuery(title);
            }
            for (Estate estate1 : estateList) {
                boolean mylike = favoriteRepository.existsByEstateidAndUserid(estate1.getId(), userDetails.getId());
                EstateResponseDto estateResponseDto = new EstateResponseDto(estate1,title, mylike);
                estate.add(estateResponseDto);
            }
            String response = convertAddress.convertAddress(title);
            CoordinateResponseDto coordinateResponseDtoDtoDto = convertAddress.fromJSONtoItems(response);

            CityResponseDto cityResponseDto = new CityResponseDto(title, coordinateResponseDtoDtoDto, estate,avg);
            cityResponseDtoList.add(cityResponseDto);
        }
        return new MapResponseDto(level, cityResponseDtoList);
    }

    //지도 검색 조회
    public CityResponseDto showSearchonMap(int level, String query, UserDetailsImpl userDetails) {
        List<Estate> estateList = estateRepository.searchAllByCity(query);
        float avg =estateRepository.cityAvgQuery(query);
        List<EstateResponseDto> estateResponseDtoList = new ArrayList<>();
        for (Estate estate : estateList) {
            boolean myLike = favoriteRepository.existsByEstateidAndUserid(estate.getId(), userDetails.getId());
            EstateResponseDto estateResponseDto = new EstateResponseDto(estate, myLike);
            estateResponseDtoList.add(estateResponseDto);
        }
        String response = convertAddress.convertAddress(query);
        CoordinateResponseDto coordinateResponseDto = convertAddress.fromJSONtoItems(response);
        return new CityResponseDto(query, coordinateResponseDto, estateResponseDtoList,avg);
    }
}

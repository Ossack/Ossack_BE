package com.sparta.startup_be.service;



import com.sparta.startup_be.dto.FavoriteListDto;
import com.sparta.startup_be.model.Estate;
import com.sparta.startup_be.model.Favorite;
import com.sparta.startup_be.model.User;
import com.sparta.startup_be.repository.EstateRepository;
import com.sparta.startup_be.repository.FavoriteRepository;
import com.sparta.startup_be.repository.UserRepository;
import com.sparta.startup_be.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.sparta.startup_be.dto.*;
import com.sparta.startup_be.model.Coordinate;
import com.sparta.startup_be.model.Estate;
import com.sparta.startup_be.repository.CoordinateRepository;
import com.sparta.startup_be.repository.EstateRepository;
import com.sparta.startup_be.repository.FavoriteRepository;
import com.sparta.startup_be.security.UserDetailsImpl;
import com.sparta.startup_be.utils.ConvertAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public void storeEstate(List<Estate> estates){
        for(Estate estate :estates){
            estateRepository.save(estate);
        }
    }
    //지역검색하기
    public String average(String query){
        List<Estate> estates = estateRepository.findAllByCity(query);
        double sum_fee = 0;
        double sum_deposit = 0;
        for(Estate estate : estates){
            if(estate.getMonthly().equals("월세")) {
//                sum_fee += estate.getRent_fee()/estate.getArea();
            }
//            else{
//                sum_deposit += Integer.parseInt(estate.getDeposit().replace(",","")) / estate.getArea();
//            }
        }
        double avg_fee = sum_fee /estateRepository.countAllByMonthlyAndCity("월세",query)*3.3;
        double avg_deposit = sum_deposit /estateRepository.countAllByMonthlyAndCity("전세",query)*3.3;
        return "월세 평균은"+avg_fee+"이고, 전세보증금 평균은"+avg_deposit+"입니다.";
    }

    //메인페이지 해당 동 조회
    public List<EstateResponseDto> searchTown(String query,UserDetailsImpl userDetails){
        List<Estate> estates = estateRepository.searchAllByCity(query);
        List<EstateResponseDto> estateResponseDtoList = new ArrayList<>();
        for(Estate estate : estates){
            boolean mylike = favoriteRepository.existsByEstateidAndUserid(estate.getId(),userDetails.getId());
            EstateResponseDto estateResponseDto =new EstateResponseDto(estate,mylike);
            estateResponseDtoList.add(estateResponseDto);
        }
        return estateResponseDtoList;
    }

    //핫한 매물 보기기
    public List<Map<String,Object>> searchHot(UserDetailsImpl userDetails){
        List<Map<String,Object>> asd = favoriteRepository.countUseridQuery();
//        for(Map<String,Object> asdd : asd){
//            boolean mylike = favoriteRepository.existsByEstateidAndUserid(Long.valueOf(String.valueOf(asdd.get("id"))),userDetails.getId());
//            asdd.put("mylike",mylike);
//        }
        return asd;
    }

   //구별로 모아보기
    public List<Estate> guAverage(String query){
        List<Estate> estates = estateRepository.searchAllByCity(query);
        for(Estate estate : estates){
            System.out.println(estate.getCity());
        }
        return estates;
    }


    // 찜한것 보기
    public List<EstateResponseDto> showFavorite(UserDetailsImpl userDetails){

        // 찜한 매물 목록
        List<Favorite> favoriteList = favoriteRepository.findByUserid(userDetails.getId());

        List<EstateResponseDto> estateResponseDtos = new ArrayList<>();
        for(int i=0; i<favoriteList.size(); i++) {
            favoriteList.get(i).getEstateid();
            System.out.println(favoriteList.get(i).getEstateid());
            Estate estate = estateRepository.findById(favoriteList.get(i).getEstateid()).orElseThrow(
                    () -> new NullPointerException("게시글이 없습니다"));
            EstateResponseDto estateResponseDto = new EstateResponseDto(estate,true);
            estateResponseDtos.add(estateResponseDto);
        }
        return estateResponseDtos;
    }

    public MapResponseDto showEstate(float minX, float maxX, float minY, float maxY, int level, UserDetailsImpl userDetails){
        List<Coordinate> coordinates = coordinateRepository.findAllByXBetweenAndYBetween(minX,maxX,minY,maxY);
//        List<Coordinate> coordinates = coordinateRepository.findAllByXBetween(minX,maxX);
//        System.out.println(coordinates.size());
        Set<String> cities = new HashSet<>();
        for(Coordinate coordinate : coordinates){
            Estate estate2 = estateRepository.findById(coordinate.getEstateid()).orElseThrow(
                    ()-> new IllegalArgumentException("하이")
            );
            String city = "";
            if(level ==1){
                city=estate2.getCity().split(" ")[0];
            } else if (level == 2) {
                city = estate2.getCity().split(" ")[0]+" "+estate2.getCity().split(" ")[1];
            }else{
                city = estate2.getCity();
            }
            cities.add(city);
        }
        Iterator<String> it = cities.iterator();
        List<CityResponseDto> cityResponseDtoList = new ArrayList<>();
        while(it.hasNext()){
            String title = it.next();
            List<EstateResponseDto> estate = new ArrayList<>();
            List<Estate> estateList = estateRepository.searchAllByCity(title);
            for(Estate estate1 : estateList){
                boolean mylike = favoriteRepository.existsByEstateidAndUserid(estate1.getId(),userDetails.getId());
                EstateResponseDto estateResponseDto = new EstateResponseDto(estate1,mylike);
                estate.add(estateResponseDto);
            }
            String response = convertAddress.convertAddress(title);
            CoordinateResponseDto coordinateResponseDtoDtoDto = convertAddress.fromJSONtoItems(response);
            CityResponseDto cityResponseDto = new CityResponseDto(title,coordinateResponseDtoDtoDto,estate);
            cityResponseDtoList.add(cityResponseDto);
        }
        MapResponseDto mapResponseDto = new MapResponseDto(level,cityResponseDtoList);
        return mapResponseDto;
    }
}

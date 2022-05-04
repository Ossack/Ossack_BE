package com.sparta.startup_be.service;

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
    private final CoordinateRepository coordinateRepository;
    private final FavoriteRepository favoriteRepository;
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
        System.out.println(query+"in Service");
        List<Estate> estates = estateRepository.findAllByCity(query);
        double sum_fee = 0;
        double sum_deposit = 0;
        for(Estate estate : estates){
            if(estate.getMonthly().equals("월세")) {
                System.out.println("nice");
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

    //구별로 모아보기
    public List<Estate> guAverage(){
        String query="강서구";
        List<Estate> estates = estateRepository.findAllByCityLike("%"+query+"%");
        for(Estate estate : estates){
            System.out.println(estate.getCity());
        }
        return estates;
    }

    public MapResponseDto showEstate(float minX, float maxX, float minY, float maxY, int level, UserDetailsImpl userDetails){
        List<Coordinate> coordinates = coordinateRepository.findAllByXBetweenAndYBetween(minX,maxX,minY,maxY);
//        List<Coordinate> coordinates = coordinateRepository.findAllByXBetween(minX,maxX);
        System.out.println(coordinates.size());
        Set<String> cities = new HashSet<>();
        for(Coordinate coordinate : coordinates){
            Estate estate2 = estateRepository.findById(coordinate.getEstateid()).orElseThrow(
                    ()-> new IllegalArgumentException("하이")
            );
            cities.add(estate2.getCity());
        }
        Iterator<String> it = cities.iterator();
        List<CityResponseDto> cityResponseDtoList = new ArrayList<>();
        while(it.hasNext()){
            String title = it.next();
            List<EstateResponseDto> estate = new ArrayList<>();
            List<Estate> estateList = estateRepository.findAllByCity(title);
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

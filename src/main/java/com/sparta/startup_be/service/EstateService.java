package com.sparta.startup_be.service;

import com.sparta.startup_be.dto.EstateResponseDto;
import com.sparta.startup_be.model.Coordinate;
import com.sparta.startup_be.model.Estate;
import com.sparta.startup_be.repository.CoordinateRepository;
import com.sparta.startup_be.repository.EstateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EstateService {
    private final EstateRepository estateRepository;
    private final CoordinateRepository coordinateRepository;

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

    public void showEstate(float minX,float maxX, float minY, float maxY,int level){
        List<Coordinate> coordinates = coordinateRepository.findAllByXGreaterThanAndXLessThanAndYGreaterThanAndYLessThan(minX,maxX,minY,maxY);
        for(Coordinate coordinate : coordinates){
            Estate estate = estateRepository.findById(coordinate.getEstateid()).orElseThrow(
                    ()-> new IllegalArgumentException("")
            );
//            EstateResponseDto estateResponseDto = new EstateResponseDto(estate);
        }
    }

}

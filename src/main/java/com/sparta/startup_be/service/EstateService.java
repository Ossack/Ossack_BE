package com.sparta.startup_be.service;

import com.sparta.startup_be.dto.EstateDto;
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

@RequiredArgsConstructor
@Service
public class EstateService {
    private final EstateRepository estateRepository;
    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;

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

    // 찜한것 보기
    public List<FavoriteListDto> showFavorite(UserDetailsImpl userDetails){

        // 찜한 매물 목록
        List<Favorite> favoriteList = favoriteRepository.findByUserid(userDetails.getId());
        System.out.println(favoriteList);

        List<FavoriteListDto> favoriteListDtos = new ArrayList<>();
        for(int i=0; i<favoriteList.size(); i++) {
            favoriteList.get(i).getEstateid();
            System.out.println(favoriteList.get(i).getEstateid());
            Estate estate = estateRepository.findById(favoriteList.get(i).getEstateid()).orElseThrow(
                    () -> new NullPointerException("dfdfdf"));
            FavoriteListDto favoriteListDto = new FavoriteListDto(estate,true);
            favoriteListDtos.add(favoriteListDto);
        }
        return favoriteListDtos;
    }
}

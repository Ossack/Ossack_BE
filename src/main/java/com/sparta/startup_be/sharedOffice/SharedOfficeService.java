package com.sparta.startup_be.sharedOffice;

import com.sparta.startup_be.coordinate.dto.CoordinateResponseDto;
import com.sparta.startup_be.coordinate.repository.CoordinateSharedOfficeRepository;
import com.sparta.startup_be.estate.dto.CityResponseDto;
import com.sparta.startup_be.estate.dto.EstateResponseDto;
import com.sparta.startup_be.estate.dto.MapResponseDto;
import com.sparta.startup_be.estate.dto.SearchDto;
import com.sparta.startup_be.favorite.FavoriteRepository;
import com.sparta.startup_be.login.model.User;
import com.sparta.startup_be.login.security.UserDetailsImpl;
import com.sparta.startup_be.model.*;
import com.sparta.startup_be.sharedOffice.dto.SearchSharedOfficeResponseDto;
import com.sparta.startup_be.sharedOffice.dto.SharedOfficeResponseDto;
import com.sparta.startup_be.utils.ConvertAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SharedOfficeService {
    private final SharedOfficeRepository sharedOfficeRepository;
    private final ConvertAddress convertAddress;
    private final FavoriteRepository favoriteRepository;
    private final CoordinateSharedOfficeRepository coordinateSharedOfficeRepository;
    public void storeSharedOffice(List<SharedOffice> sharedOffices) {
        for (SharedOffice sharedOffice : sharedOffices) {
            sharedOfficeRepository.save(sharedOffice);
        }
    }

    public MapResponseDto showSharedOffice(float minX, float maxX, float minY, float maxY, int level, UserDetailsImpl userDetails) {
        long temp1 = System.currentTimeMillis();

//        List<String> cities = estateRepository.findCity(minX,maxX,minY,maxY);
        List<String> cities = new ArrayList<>();

        if (level < 7) {
            cities = sharedOfficeRepository.findDongQuery(minX, maxX, minY, maxY);
        } else if (level == 7 || level == 8) {
            cities = sharedOfficeRepository.findGuQuery(minX, maxX, minY, maxY);
        } else {
            cities = sharedOfficeRepository.findCityQuery(minX, maxX, minY, maxY);
        }
        long temp2 = System.currentTimeMillis();
        System.out.println("temp1:");
        System.out.println(temp2 - temp1);
//        System.out.println("size"+cities2.size());
//        Iterator<String> it = cities.iterator();
        List<CityResponseDto> cityResponseDtoList = new ArrayList<>();
        for (int i = 0; i < cities.size(); i++) {
            String title = cities.get(i);
            System.out.println(title);
            List<EstateResponseDto> estate = new ArrayList<>();
            int estate_cnt = 0;
            float avg = 0f;
            if (level < 7) {
                estate_cnt = sharedOfficeRepository.countAllByDongQuery(title);
            } else if (level == 7 || level == 8) {
                estate_cnt = sharedOfficeRepository.countAllByGuQuery(title);
            } else {
                estate_cnt = sharedOfficeRepository.countAllByCityQuery(title);
            }
            avg = Integer.parseInt(String.valueOf(Math.round(avg)));
            String response = convertAddress.convertAddress(title);
            CoordinateResponseDto coordinateResponseDtoDtoDto = convertAddress.fromJSONtoItems(response);

            CityResponseDto cityResponseDto = new CityResponseDto(title, coordinateResponseDtoDtoDto, estate_cnt, (int) avg);
            cityResponseDtoList.add(cityResponseDto);
        }
        long temp3 = System.currentTimeMillis();
        System.out.println("temp2:");
        System.out.println(temp3 - temp2);
        System.out.println("총:");
        System.out.println(temp3 - temp1);
        return new MapResponseDto(level, cityResponseDtoList);
    }

    public SearchSharedOfficeResponseDto searchTowm(String query, UserDetailsImpl userDetails, int pagenum) {
        List<SharedOfficeResponseDto> sharedOfficeResponseDtos = new ArrayList<>();
        final int start = 10 * pagenum;
        List<SharedOffice> sharedOffices = sharedOfficeRepository.searchAllByQuery(query,start);
        int size = sharedOfficeRepository.countAllByQuery(query);
//        if (query.contains("시")) {
//            sharedOffices = sharedOfficeRepository.searchAllByCityQuery(query,start);
//            size = sharedOfficeRepository.countAllByCityQuery(query);
//        } else if (query.contains("구")) {
//            sharedOffices = sharedOfficeRepository.searchAllByGuQuery(query,start);
//            size = sharedOfficeRepository.countAllByGuQuery(query);
//        } else {
//            sharedOffices = sharedOfficeRepository.searchAllByDongQuery(query,start);
//            size = sharedOfficeRepository.countAllByDongQuery(query);
//        }

        for (SharedOffice sharedOffice : sharedOffices) {
            boolean mylike = favoriteRepository.existsByEstateidAndUserid(sharedOffice.getId(), userDetails.getId());
            CoordinateSharedOffice coordinateSharedOffice = coordinateSharedOfficeRepository.findBySharedofficeid(sharedOffice.getId());
            CoordinateResponseDto coordinateResponseDto = new CoordinateResponseDto(coordinateSharedOffice);
            SharedOfficeResponseDto sharedOfficeResponseDto = new SharedOfficeResponseDto(sharedOffice, query, mylike,coordinateResponseDto);
            sharedOfficeResponseDtos.add(sharedOfficeResponseDto);
        }

        int totalpage;
        if (size % 10 == 0) {
            totalpage = size / 10;
        } else {
            totalpage = size/10 + 1;
        }
        SearchSharedOfficeResponseDto searchSharedOfficeResponseDto = new SearchSharedOfficeResponseDto(sharedOfficeResponseDtos, totalpage, pagenum + 1);
        System.out.println(searchSharedOfficeResponseDto.getPresentpage());
        System.out.println(searchSharedOfficeResponseDto.getTotalpage());
        System.out.println(searchSharedOfficeResponseDto.getSharedOfficeResponseDtos().size());
        return searchSharedOfficeResponseDto;
    }

    public SharedOfficeResponseDto showdetail(Long shareofficeid, UserDetailsImpl userDetails){
        SharedOffice sharedOffice = sharedOfficeRepository.findById(shareofficeid).orElseThrow(
                ()-> new IllegalArgumentException("사라진 정보입니다.")
        );
        CoordinateSharedOffice coordinateSharedOffice = coordinateSharedOfficeRepository.findBySharedofficeid(shareofficeid);
        CoordinateResponseDto coordinateResponseDto = new CoordinateResponseDto(coordinateSharedOffice);
        boolean mylike = favoriteRepository.existsByEstateidAndUserid(shareofficeid, userDetails.getId());
        return new SharedOfficeResponseDto(sharedOffice,"",mylike,coordinateResponseDto);
    }

    public List<SharedOfficeResponseDto> showMySharedOffice(User user){
        List<Favorite> favorites = favoriteRepository.findByUserid(user.getId());
        List<SharedOfficeResponseDto> sharedOfficeResponseDtos = new ArrayList<>();
        for(int i=0; i<favorites.size(); i++){
            SharedOffice sharedOffice = sharedOfficeRepository.findById(favorites.get(i).getEstateid()).orElseThrow(
                    ()-> new IllegalArgumentException("이미 사라진 매물입니다")
            );
            SharedOfficeResponseDto sharedOfficeResponseDto = new SharedOfficeResponseDto(sharedOffice,true);
            sharedOfficeResponseDtos.add(sharedOfficeResponseDto);
        }
        return sharedOfficeResponseDtos;
    }

    public List<SharedOfficeResponseDto> showFavorite(UserDetailsImpl userDetails) {

        // 찜한 매물 목록
        List<Favorite> favoriteList = favoriteRepository.findAllByUseridAndType(userDetails.getId(),"공유오피스");

        List<SharedOfficeResponseDto> sharedOfficeResponseDtos = new ArrayList<>();
        for (int i = 0; i < favoriteList.size(); i++) {
            favoriteList.get(i).getEstateid();
            System.out.println(favoriteList.get(i).getEstateid());
            SharedOffice sharedOffice = sharedOfficeRepository.findById(favoriteList.get(i).getEstateid()).orElseThrow(
                    () -> new NullPointerException("게시글이 없습니다"));
            SharedOfficeResponseDto sharedOfficeResponseDto = new SharedOfficeResponseDto(sharedOffice, true);
            sharedOfficeResponseDtos.add(sharedOfficeResponseDto);
        }
        return sharedOfficeResponseDtos;
    }
}

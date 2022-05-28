package com.sparta.startup_be.sharedOffice;

import com.sparta.startup_be.coordinate.dto.CoordinateResponseDto;
import com.sparta.startup_be.coordinate.repository.CoordinateSharedOfficeRepository;
import com.sparta.startup_be.estate.dto.CityResponseDto;
import com.sparta.startup_be.estate.dto.EstateResponseDto;
import com.sparta.startup_be.estate.dto.MapResponseDto;
import com.sparta.startup_be.favorite.FavoriteRepository;
import com.sparta.startup_be.login.model.User;
import com.sparta.startup_be.login.security.UserDetailsImpl;
import com.sparta.startup_be.model.*;
import com.sparta.startup_be.sharedOffice.dto.SearchSharedOfficeResponseDto;
import com.sparta.startup_be.sharedOffice.dto.SharedOfficeResponseDto;
import com.sparta.startup_be.utils.ConvertAddress;
import com.sparta.startup_be.utils.NaverSearchApi;
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

    private final NaverSearchApi naverSearchApi;
    public void storeSharedOffice(List<SharedOffice> sharedOffices) {
        for (SharedOffice sharedOffice : sharedOffices) {
            sharedOfficeRepository.save(sharedOffice);
        }
    }

    public MapResponseDto showSharedOffice(float minX, float maxX, float minY, float maxY, int level) throws InterruptedException {

//        List<String> cities = estateRepository.findCity(minX,maxX,minY,maxY);

        List<String> cities;


        if (level < 7) {
            cities = sharedOfficeRepository.findSharedOfficebyDongQuery(minX, maxX, minY, maxY);
        } else if (level == 7 || level == 8) {
            cities = sharedOfficeRepository.findSharedOfficebyGuQuery(minX, maxX, minY, maxY);
        } else {
            cities = sharedOfficeRepository.findSharedOfficebyCityQuery(minX, maxX, minY, maxY);
        }
        for(String city : cities){
            System.out.println(city);
        }

//        System.out.println("size"+cities2.size());
//        Iterator<String> it = cities.iterator();
        long temp2 = System.currentTimeMillis();

        List<CityResponseDto> cityResponseDtoList = new ArrayList<>();
        for (int i = 0; i < cities.size(); i++) {
            String title = cities.get(i);

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
            CoordinateResponseDto coordinateResponseDtoDtoDto = naverSearchApi.getCoordinate(title);
            CityResponseDto cityResponseDto = new CityResponseDto(title, coordinateResponseDtoDtoDto, estate_cnt, (int) avg);
            cityResponseDtoList.add(cityResponseDto);
        }
        long temp3 = System.currentTimeMillis();
        System.out.println("temp2:");
        System.out.println(temp3 - temp2);

        return new MapResponseDto(level, cityResponseDtoList);
    }

    public SearchSharedOfficeResponseDto searchTowm(String query, UserDetailsImpl userDetails, int pagenum) throws InterruptedException {
        List<SharedOfficeResponseDto> sharedOfficeResponseDtos = new ArrayList<>();
        String keyword = naverSearchApi.getQuery(query);
        final int start = 10 * pagenum;

        List<SharedOffice> sharedOffices = sharedOfficeRepository.searchSharedOfficeByQuery(query,keyword,start);
        int size = sharedOfficeRepository.countSharedOfficeByQuery(query,keyword);

        System.out.println(keyword);
        for (SharedOffice sharedOffice : sharedOffices) {
            boolean mylike = favoriteRepository.existsByEstateidAndUserid(sharedOffice.getId(), userDetails.getId());
            CoordinateSharedOffice coordinateSharedOffice = coordinateSharedOfficeRepository.findBySharedofficeid(sharedOffice.getId());
            CoordinateResponseDto coordinateResponseDto = new CoordinateResponseDto(coordinateSharedOffice);
            String address = naverSearchApi.getAddress(sharedOffice.getName());
            SharedOfficeResponseDto sharedOfficeResponseDto = new SharedOfficeResponseDto(sharedOffice, keyword, mylike,coordinateResponseDto,address);
            sharedOfficeResponseDtos.add(sharedOfficeResponseDto);
        }

        int totalpage;
        if (size % 10 == 0) {
            totalpage = size / 10;
        } else {
            totalpage = size/10 + 1;
        }
        return new SearchSharedOfficeResponseDto(sharedOfficeResponseDtos, totalpage, pagenum + 1,query);
    }

    public SharedOfficeResponseDto showdetail(Long shareofficeid, UserDetailsImpl userDetails) throws InterruptedException {
        SharedOffice sharedOffice = sharedOfficeRepository.findById(shareofficeid).orElseThrow(
                ()-> new IllegalArgumentException("사라진 정보입니다.")
        );
        CoordinateSharedOffice coordinateSharedOffice = coordinateSharedOfficeRepository.findBySharedofficeid(shareofficeid);
        CoordinateResponseDto coordinateResponseDto = new CoordinateResponseDto(coordinateSharedOffice);
        boolean mylike = favoriteRepository.existsByEstateidAndUserid(shareofficeid, userDetails.getId());
        String address = naverSearchApi.getAddress(sharedOffice.getName());
        return new SharedOfficeResponseDto(sharedOffice,"",mylike,coordinateResponseDto,address);
    }

    public List<SharedOfficeResponseDto> showMySharedOffice(User user) throws InterruptedException {
        List<Favorite> favorites = favoriteRepository.findAllByUseridAndType(user.getId(),"공유오피스");
        List<SharedOfficeResponseDto> sharedOfficeResponseDtos = new ArrayList<>();
        for(int i=0; i<favorites.size(); i++){
            SharedOffice sharedOffice = sharedOfficeRepository.findById(favorites.get(i).getEstateid()).orElseThrow(
                    ()-> new IllegalArgumentException("이미 사라진 매물입니다")
            );
            String city =sharedOffice.getCity()+" "+ sharedOffice.getGu() +" "+ sharedOffice.getDong();
            String address = naverSearchApi.getAddress(sharedOffice.getName());
            SharedOfficeResponseDto sharedOfficeResponseDto = new SharedOfficeResponseDto(sharedOffice,true,address);
            sharedOfficeResponseDtos.add(sharedOfficeResponseDto);
        }
        return sharedOfficeResponseDtos;
    }

    public List<SharedOfficeResponseDto> showFavorite(UserDetailsImpl userDetails) throws InterruptedException {

        // 찜한 매물 목록
        List<Favorite> favoriteList = favoriteRepository.findAllByUseridAndType(userDetails.getId(),"공유오피스");

        List<SharedOfficeResponseDto> sharedOfficeResponseDtos = new ArrayList<>();
        for (int i = 0; i < favoriteList.size(); i++) {
            favoriteList.get(i).getEstateid();
            SharedOffice sharedOffice = sharedOfficeRepository.findById(favoriteList.get(i).getEstateid()).orElseThrow(
                    () -> new NullPointerException("게시글이 없습니다"));
            String address = naverSearchApi.getAddress(sharedOffice.getName());

            SharedOfficeResponseDto sharedOfficeResponseDto = new SharedOfficeResponseDto(sharedOffice, true,address);
            sharedOfficeResponseDtos.add(sharedOfficeResponseDto);
        }
        return sharedOfficeResponseDtos;
    }
}

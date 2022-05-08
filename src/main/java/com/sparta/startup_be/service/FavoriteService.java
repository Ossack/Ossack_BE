package com.sparta.startup_be.service;


import com.sparta.startup_be.dto.FavoriteDto;
import com.sparta.startup_be.model.Favorite;
import com.sparta.startup_be.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;

    public void pressLike(Long estateid, Long userid){
        Optional<Favorite> favorite = favoriteRepository.findByUseridAndEstateid(userid,estateid);
        if(favorite.isPresent()){
            throw new IllegalArgumentException("이미 눌러진 좋아요 입니다");
        }
        FavoriteDto favoriteDto = new FavoriteDto(userid,estateid);
        Favorite favorite1 = new Favorite(favoriteDto);
        favoriteRepository.save(favorite1);
    }


    @Transactional
    public void unpressLike(Long estateid,Long userid){
        Favorite favorite = favoriteRepository.findByUseridAndEstateid(userid,estateid).orElseThrow(
                ()-> new IllegalArgumentException("이미 좋아요가 취소되었습니다")
        );
        favoriteRepository.deleteByUseridAndEstateid(userid, estateid);
    }
}


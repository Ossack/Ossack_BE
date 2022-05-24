package com.sparta.startup_be.favorite;


import com.sparta.startup_be.estate.EstateRepository;
import com.sparta.startup_be.favorite.dto.FavoriteRequestDto;
import com.sparta.startup_be.favorite.dto.MylikeDto;
import com.sparta.startup_be.exception.StatusMessage;
import com.sparta.startup_be.model.Estate;
import com.sparta.startup_be.model.Favorite;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.charset.Charset;
import java.util.Optional;

import static com.sparta.startup_be.exception.ExceptionMessage.ILLEGAL_ALREADY_LIKE_CANCEL;
import static com.sparta.startup_be.exception.ExceptionMessage.ILLEGAL_ALREADY_LIKE_EXIST;


@RequiredArgsConstructor
@Service
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final EstateRepository estateRepository;


    public ResponseEntity<MylikeDto> pressLike(Long estateid, Long userid){
        Optional<Favorite> favorite = favoriteRepository.findByUseridAndEstateid(userid, estateid);
        if (favorite.isPresent()) {
            throw new IllegalArgumentException(ILLEGAL_ALREADY_LIKE_EXIST);
        }
        Optional<Estate> estate = estateRepository.findById(estateid);
        FavoriteRequestDto favoriteRequestDto = new FavoriteRequestDto();
        if (estate.isPresent()) {
            favoriteRequestDto = new FavoriteRequestDto(userid, estateid, "사무실");
        } else {
            favoriteRequestDto = new FavoriteRequestDto(userid, estateid, "공유오피스");
        }
            Favorite favorite1 = new Favorite(favoriteRequestDto);
            favoriteRepository.save(favorite1);

        MylikeDto mylikeDto = new MylikeDto(true);

        return ResponseEntity.status(200)
                .body(mylikeDto);
    }


    @Transactional
    public ResponseEntity<MylikeDto> unpressLike(Long estateid,Long userid){
        Favorite favorite = favoriteRepository.findByUseridAndEstateid(userid,estateid).orElseThrow(
                ()-> new IllegalArgumentException(ILLEGAL_ALREADY_LIKE_CANCEL)
        );
        favoriteRepository.deleteByUseridAndEstateid(userid, estateid);

        MylikeDto mylikeDto = new MylikeDto(false);

        return ResponseEntity.status(200)
                .body(mylikeDto);
    }
}


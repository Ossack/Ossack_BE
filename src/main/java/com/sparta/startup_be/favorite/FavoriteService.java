package com.sparta.startup_be.favorite;


import com.sparta.startup_be.estate.EstateRepository;
import com.sparta.startup_be.favorite.dto.FavoriteRequestDto;
import com.sparta.startup_be.model.Estate;
import com.sparta.startup_be.model.Favorite;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.sparta.startup_be.exception.ExceptionMessage.ILLEGAL_ALREADY_LIKE_CANCEL;
import static com.sparta.startup_be.exception.ExceptionMessage.ILLEGAL_ALREADY_LIKE_EXIST;


@RequiredArgsConstructor
@Service
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final EstateRepository estateRepository;

    public ResponseEntity<Boolean> pressLike(Long estateid, Long userid) {

        // 매물에 맞는 좋아요가 있으면 에러 발생
        Optional<Favorite> favorite = favoriteRepository.findByUseridAndEstateid(userid, estateid);
        if (favorite.isPresent()) {
            throw new IllegalArgumentException(ILLEGAL_ALREADY_LIKE_EXIST);
        }

        // 사무실 테이블에
        Optional<Estate> estate = estateRepository.findById(estateid);
        FavoriteRequestDto favoriteRequestDto;
        if (estate.isPresent()) {
            favoriteRequestDto = new FavoriteRequestDto(userid, estateid, "사무실");
        } else {
            favoriteRequestDto = new FavoriteRequestDto(userid, estateid, "공유오피스");
        }

        favoriteRepository.save(new Favorite(favoriteRequestDto));
        return ResponseEntity.status(200)
                .body(true);
    }
    @Transactional
    public ResponseEntity<Boolean> unpressLike(Long estateid, Long userid) {

        // 매물에 맞는 좋아요가 없으면 에러 발생
        favoriteRepository.findByUseridAndEstateid(userid, estateid).orElseThrow(
                ()-> new IllegalArgumentException(ILLEGAL_ALREADY_LIKE_CANCEL)
        );

        // 매물에 맞는 좋아요가 있으면 좋아요 삭제
        favoriteRepository.deleteByUseridAndEstateid(userid, estateid);

        return ResponseEntity.status(200)
                .body(false);
    }
}


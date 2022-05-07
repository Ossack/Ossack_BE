package com.sparta.startup_be.controller;


import com.sparta.startup_be.dto.MylikeDto;
import com.sparta.startup_be.security.UserDetailsImpl;
import com.sparta.startup_be.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class FavoriteController {
    private final FavoriteService favoriteService;

    @ExceptionHandler(IllegalArgumentException.class)
    public String nullex(IllegalArgumentException e) {
        return e.getMessage();
    }

    @PostMapping("/api/favorite/{estateid}")
    public MylikeDto pressLike(@PathVariable Long estateid,
                               @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long userid = userDetails.getUser().getId();
        favoriteService.pressLike(estateid,userid);
        return new MylikeDto(true);
    }

    @DeleteMapping("/api/favorite/{estateid}")
    public MylikeDto unpresslike(@PathVariable Long estateid,
                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long userid = userDetails.getUser().getId();
        favoriteService.unpressLike(estateid,userid);
        return new MylikeDto(false);
    }
}

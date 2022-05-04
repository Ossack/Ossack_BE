package com.sparta.startup_be.controller;


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
    public String pressLike(@PathVariable Long estateid,
                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long userid = userDetails.getUser().getId();
        favoriteService.pressLike(estateid,userid);
        return "좋아요 눌름";
    }

    @DeleteMapping("/api/favorite/{estateid}")
    public String unpresslike(@PathVariable Long estateid,
                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long userid = userDetails.getUser().getId();
        favoriteService.unpressLike(estateid,userid);
        return "좋아요 취소";
    }
}

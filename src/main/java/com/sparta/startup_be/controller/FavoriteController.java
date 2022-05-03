package com.sparta.startup_be.controller;


import com.sparta.startup_be.dto.FavoriteResponseDto;
import com.sparta.startup_be.security.UserDetailsImpl;
import com.sparta.startup_be.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FavoriteController {

    private final FavoriteService favoriteService;

    // 찜하기 기능
    @PostMapping("/api/Favorite/{estateId}")
    public FavoriteResponseDto favoriteCheck(@PathVariable Long estateId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return favoriteService.favoriteCheck(estateId,userDetails.getUsername());
    }
}

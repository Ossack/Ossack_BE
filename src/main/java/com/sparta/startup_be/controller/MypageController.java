package com.sparta.startup_be.controller;

import com.sparta.startup_be.dto.UserResponseDto;
import com.sparta.startup_be.security.UserDetailsImpl;
import com.sparta.startup_be.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;

    // 프로필 수정
    @PutMapping("/api/user/profile")
    public UserResponseDto updateImg (@RequestParam("imageFile") MultipartFile multipartFile,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mypageService.updateProfile(multipartFile, userDetails);
    }
}

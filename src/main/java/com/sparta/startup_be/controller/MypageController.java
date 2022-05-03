package com.sparta.startup_be.controller;

import com.sparta.startup_be.dto.ProfileResponseDto;
import com.sparta.startup_be.security.UserDetailsImpl;
import com.sparta.startup_be.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;

    // 프로필 수정
    @PutMapping("/api/user/profile")
    public ProfileResponseDto updateImg (@RequestParam("profileImg") MultipartFile multipartFile,
//                                                         @RequestParam("nickname") String nickname,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ProfileResponseDto profileResponseDto = mypageService.updateProfile(multipartFile, userDetails);
        return profileResponseDto;
    }
//
//    // 프로필 조회
//    @GetMapping("/api/user/profile")


}

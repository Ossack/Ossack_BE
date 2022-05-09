package com.sparta.startup_be.controller;

import com.sparta.startup_be.dto.UserResponseDto;
import com.sparta.startup_be.security.UserDetailsImpl;
import com.sparta.startup_be.service.MypageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@Api(tags = {"마이페이지 내 기능 Controller"})
@RestController
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;

    // 프로필 수정
    @ApiOperation(value ="프로필 수정 메소드")
    @PutMapping("/api/user/profile")
    public UserResponseDto updateImg (@RequestParam("imageFile") MultipartFile multipartFile,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mypageService.updateProfile(multipartFile, userDetails);
    }
}

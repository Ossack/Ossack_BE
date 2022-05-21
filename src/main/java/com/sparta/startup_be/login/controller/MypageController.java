package com.sparta.startup_be.login.controller;

import com.sparta.startup_be.exception.StatusMessage;
import com.sparta.startup_be.login.security.UserDetailsImpl;
import com.sparta.startup_be.login.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;

    // 프로필 이미지 수정
    @PutMapping("/user/profile")
    public ResponseEntity<StatusMessage> updateImg (@RequestParam("imageFile") MultipartFile multipartFile,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mypageService.updateProfile(multipartFile, userDetails);
    }

    // 회원 로그인 여부 확인
    @GetMapping("/user/islogin")
    public ResponseEntity<StatusMessage> isLogin(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mypageService.isLogin(userDetails);
    }
}

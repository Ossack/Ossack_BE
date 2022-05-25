package com.sparta.startup_be.login.controller;

import com.sparta.startup_be.login.dto.UserResponseDto;
import com.sparta.startup_be.login.security.UserDetailsImpl;
import com.sparta.startup_be.login.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;

    // 프로필 수정
    @PutMapping("/user/profile")
    public ResponseEntity<String> updateProfile (@RequestParam("imageFile") MultipartFile multipartFile,
                                                    @RequestParam ("nickname") String nickname,
                                                    @RequestParam String profileImgUrl,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        if (nickname == null || nickname.isEmpty()) {
            nickname = userDetails.getUser().getNickname();
        }
        // 닉네임은 변경하고, 이미지는 삭제 혹은 미변경
        if (multipartFile == null || multipartFile.isEmpty()){
            mypageService.deleteImg(profileImgUrl, nickname, userDetails);
        } else {
            // 사용자가 이미지를 수정함
            mypageService.updateProfile(multipartFile, nickname, userDetails);
        }
        return ResponseEntity.status(200).body("200");
    }

    // 회원 로그인 여부 확인
    @GetMapping("/user/islogin")
    public ResponseEntity<UserResponseDto> isLogin(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mypageService.isLogin(userDetails);
    }

    // 회원 로그인 여부 확인
    @PutMapping("/user/withdraw")
    public ResponseEntity<UserResponseDto> withdrawUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mypageService.withdrawUser(userDetails);
    }
}

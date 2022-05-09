package com.sparta.startup_be.controller;

import com.fasterxml.jackson.core.JsonProcessingException;


import com.sparta.startup_be.dto.SocialUserInfoDto;
import com.sparta.startup_be.service.GoogleUserService;

import com.sparta.startup_be.service.KakaoUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
@Api(tags = {"소셜로그인 기능 Controller"})
@RestController
@RequiredArgsConstructor
public class SocialLoginController {
    private final KakaoUserService kakaoUserService;
    private final GoogleUserService googleUserService;

    // 카카오 로그인
    @ApiOperation(value = "카카오 로그인")
   @GetMapping("/user/kakao/callback")
    public SocialUserInfoDto kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        return kakaoUserService.kakaoLogin(code, response);
    }

    // 구글 로그인
    @ApiOperation(value = "구글 로그인")
    @GetMapping("/user/google/callback")
    public SocialUserInfoDto googleLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        return googleUserService.googleLogin(code, response);
    }
//
//    // 네이버 로그인
//    @GetMapping("/user/naver/callback")
//    public void naverLogin(@RequestParam String code, @RequestParam String state, HttpServletResponse response) throws JsonProcessingException{
//        naverUserService.naverLogin(code, state, response);
//    }
}

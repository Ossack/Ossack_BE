package com.sparta.startup_be.login.controller;

import com.sparta.startup_be.exception.StatusMessage;
import com.sparta.startup_be.login.dto.IdcheckDto;
import com.sparta.startup_be.login.dto.UserRequestDto;
import com.sparta.startup_be.login.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 회원가입 등록
    @PostMapping("/user/signup")
    public ResponseEntity<StatusMessage> join(
            @Validated @RequestBody UserRequestDto requestDto, BindingResult bindingResult) {
        return userService.signup(requestDto, bindingResult);
    }

    // 이메일 중복 확인
    @PostMapping("/user/idcheck")
    public ResponseEntity<StatusMessage> idCheck(
            @Validated @RequestBody IdcheckDto idcheckDto, BindingResult bindingResult) {
        return userService.dupEmail(idcheckDto, bindingResult);
    }
}

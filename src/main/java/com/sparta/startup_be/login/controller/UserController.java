package com.sparta.startup_be.login.controller;

import com.sparta.startup_be.exception.StatusMessage;
import com.sparta.startup_be.login.dto.SignupRequestDto;
import com.sparta.startup_be.login.dto.UserRequestDto;
import com.sparta.startup_be.login.repository.UserRepository;
import com.sparta.startup_be.login.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    // 회원가입 등록
    @PostMapping("/user/signup")
    public ResponseEntity<StatusMessage> join(
            @Validated @RequestBody SignupRequestDto requestDto, BindingResult bindingResult) {
        // 유효성 검증을 통해 유효하지 않은 결과를 JudgeSuccessDto에 담아서 클라이언트에게 보내준다.
        if (bindingResult.hasErrors()) {
            // 유효성 검사에 실패했을 경우 Error를 리스트 형식으로 가져온다.
            List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());

            StatusMessage message = new StatusMessage();
            HttpHeaders headers= new HttpHeaders();
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

            message.setStatusCode(StatusMessage.StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage("회원 가입 실패");

            return new ResponseEntity<>(message, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return userService.save(requestDto);
    }


    // 이메일 중복 확인
    @PostMapping("/api/idcheck")
    public ResponseEntity<StatusMessage> idCheck(@RequestBody UserRequestDto userDto) {
        return userService.dupEmail(userDto);
    }
}

package com.sparta.startup_be.login.controller;

import com.sparta.startup_be.dto.ResultDto;
import com.sparta.startup_be.login.dto.SignupRequestDto;
import com.sparta.startup_be.login.dto.UserRequestDto;
import com.sparta.startup_be.login.dto.UserResponseDto;
import com.sparta.startup_be.login.repository.UserRepository;
import com.sparta.startup_be.security.UserDetailsImpl;
import com.sparta.startup_be.login.model.User;
import com.sparta.startup_be.login.service.UserService;
import com.sparta.startup_be.exception.StatusMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

import static com.sparta.startup_be.exception.ExceptionMessage.ILLEGAL_USER_NOT_EXIST;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

//    @ExceptionHandler(IllegalArgumentException.class)
//    public String nullex(IllegalArgumentException e) {
//        return e.getMessage();
//    }
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

    // 회원 로그인 여부 확인
    @GetMapping("/api/islogin")
    public ResponseEntity<StatusMessage> isLogin(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserResponseDto userResponseDto = new UserResponseDto(userDetails.getUser(), "https://ossack.s3.ap-northeast-2.amazonaws.com/" + userDetails.getUser().getProfile());

        StatusMessage message = new StatusMessage();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatusCode(StatusMessage.StatusEnum.OK);
        message.setMessage("유저 정보 조회");
        message.setData(userResponseDto);

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    // 이메일 중복 확인
    @PostMapping("/api/idcheck")
    public ResultDto idCheck(@RequestBody UserRequestDto userDto) {
        if (userRepository.findByUserEmail(userDto.getUserEmail()).isPresent()) {
            return new ResultDto("이미 존재하는 아이디 입니다.");
        }

        return new ResultDto("사용할 수 있는 아이디 입니다.");
    }

    // 닉네임 중복 확인
    @PostMapping("/api/nickname")
    public ResultDto nnCheck(@RequestBody UserRequestDto userDto) {
        if (userRepository.findByNickname(userDto.getNickname()).isPresent()) {
            return new ResultDto("이미 존재하는 닉네임 입니다.");
        }

        return new ResultDto("사용할 수 있는 닉네임임 입니다.");
    }

//    @GetMapping("/user/{id}")
//    public ResponseEntity<StatusMessage> findById(@PathVariable Long id) {
//        User user = userRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException(ILLEGAL_USER_NOT_EXIST)
//        );
//        StatusMessage message = new StatusMessage();
//        HttpHeaders headers= new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//
//        message.setStatusCode(StatusMessage.StatusEnum.OK);
//        message.setMessage("200");
//        message.setData(user);
//
//        return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    }
}

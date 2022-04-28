package com.sparta.startup_be.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.startup_be.dto.KakaoUserInfoDto;
import com.sparta.startup_be.dto.ResultDto;
import com.sparta.startup_be.dto.SignupRequestDto;
import com.sparta.startup_be.model.User;
import com.sparta.startup_be.repository.UserRepository;
import com.sparta.startup_be.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

import static com.sparta.startup_be.exception.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    // 회원가입
    public ResultDto save(SignupRequestDto requestDto) {
        String userEmail = requestDto.getUserEmail();
        String nickname = requestDto.getNickname();
        String message;
        Optional<User> foundUser = userRepository.findByUserEmail(userEmail);
        Optional<User> foundNick = userRepository.findByNickname(nickname);

        // userEmail 중복 체크
        if(validatedDuplicateUserEmail(foundUser)) {
            message = ILLEGAL_USER_NAME_DUPLICATION;
            return new ResultDto(message);
        }

        // nickname 중복 체크
        if(validatedDuplicateNickname(foundNick)) {
            message = ILLEGAL_NICK_NAME_DUPLICATION;
            return new ResultDto(message);
        }

        String profile = "https://mwmw1.s3.ap-northeast-2.amazonaws.com/basicprofile.png";

        // 패스워드 암호화
        String password = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(userEmail, nickname, profile, password);
        userRepository.save(user);
        message = "회원가입 성공";
        return new ResultDto(message);
    }

    // 유저이름 중복 체크
    private boolean validatedDuplicateUserEmail(Optional<User> found) {
        return found.isPresent();
    }
    // 닉네임 중복 체크
    private boolean validatedDuplicateNickname(Optional<User> found) {
        return found.isPresent();
    }

}

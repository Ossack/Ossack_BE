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

    public void kakaoLogin(String code) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getAccessToken(code);

        // 2. 토큰으로 카카오 API 호출
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);

        // DB 에 중복된 Kakao Id 가 있는지 확인
        Long kakaoId = kakaoUserInfo.getId();
        User kakaoUser = userRepository.findByKakaoId(kakaoId)
                .orElse(null);
        if (kakaoUser == null) {
            // 회원가입
            // username: kakao nickname
            String nickname = kakaoUserInfo.getNickname();

            String profile = "https://mwmw1.s3.ap-northeast-2.amazonaws.com/basicprofile.png";

            // password: random UUID
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);

            // email: kakao email
            String email = kakaoUserInfo.getEmail();
            // role: 일반 사용자
//            UserRoleEnum role = UserRoleEnum.USER;

            kakaoUser = new User(email, nickname, profile, encodedPassword, kakaoId);
            userRepository.save(kakaoUser);
        }

        // 4. 강제 로그인 처리
        UserDetails userDetails = new UserDetailsImpl(kakaoUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);


    }

    private String getAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "461c917f7343f05a0bfe72bb4ae6b40a");
        body.add("redirect_uri", "http://localhost:8080/user/kakao/callback");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        return jsonNode.get("access_token").asText();
    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();

        System.out.println("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);

        return new KakaoUserInfoDto(id, nickname, email);
    }
}

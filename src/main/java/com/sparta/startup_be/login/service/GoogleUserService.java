package com.sparta.startup_be.login.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.startup_be.login.dto.SocialUserInfoDto;
import com.sparta.startup_be.login.model.User;
import com.sparta.startup_be.login.repository.UserRepository;
import com.sparta.startup_be.login.security.UserDetailsImpl;
import com.sparta.startup_be.login.security.jwt.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class GoogleUserService {

//    @Value("${spring.security.oauth2.client.registration.google.client-id}")
//    String googleClientId;
//
//    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
//    String googleClientSecret;

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    // 구글 로그인
    public SocialUserInfoDto googleLogin(String code, HttpServletResponse response) throws JsonProcessingException {

        // 1. 인가코드로 엑세스토큰 가져오기
        String accessToken = getAccessToken(code);

        // 2. 엑세스토큰으로 유저정보 가져오기
        SocialUserInfoDto googleUserInfo = getGoogleUserInfo(accessToken);

        // 3. 유저확인 & 회원가입
        User foundUser = getUser(googleUserInfo);

        // 4. 시큐리티 강제 로그인
        Authentication authentication = securityLogin(foundUser);

        // 5. jwt 토큰 발급
        jwtToken(authentication, response);
        return googleUserInfo;
    }

    // 1. 인가코드로 엑세스토큰 가져오기
    private String getAccessToken(String code) throws JsonProcessingException {

        // 헤더에 Content-type 지정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // 바디에 필요한 정보 담기
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id" , "922925499099-ae2nodkaoccn3rcd933u331j6bjnupum.apps.googleusercontent.com");
        body.add("client_secret", "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");
        body.add("code", code);
        body.add("redirect_uri", "https://ossack.shop/user/google/callback");
        body.add("grant_type", "authorization_code");

        // POST 요청 보내기
        HttpEntity<MultiValueMap<String, String>> googleToken = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                "https://oauth2.googleapis.com/token",
                HttpMethod.POST, googleToken,
                String.class
        );

        // response에서 엑세스토큰 가져오기
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseToken = objectMapper.readTree(responseBody);
        return responseToken.get("access_token").asText();
    }

    // 2. 엑세스토큰으로 유저정보 가져오기
    private SocialUserInfoDto getGoogleUserInfo(String accessToken) throws JsonProcessingException {

        // 헤더에 엑세스토큰 담기, Content-type 지정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // POST 요청 보내기
        HttpEntity<MultiValueMap<String, String>> googleUser = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                "https://openidconnect.googleapis.com/v1/userinfo",
                HttpMethod.POST, googleUser,
                String.class
        );

        // response에서 유저정보 가져오기
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        Long id = jsonNode.get("sub").asLong();
        String email = jsonNode.get("email").asText();
        String nickname = jsonNode.get("name").asText();

        return new SocialUserInfoDto(id, nickname, email);

    }

    // 3. 유저확인 & 회원가입
    private User getUser(SocialUserInfoDto googleUserInfo) {
        // DB 에 중복된 Google email, nickname이 있는지 확인
        String googleEmail = googleUserInfo.getEmail();
        String nickname = googleUserInfo.getNickname();
        User googleUser = userRepository.findByUserEmail(googleEmail)
                .orElse(null);

        if (googleUser == null) {
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);

            String profile = "https://ossack.s3.ap-northeast-2.amazonaws.com/basicprofile.png";

            googleUser = new User(googleEmail, nickname, profile, encodedPassword);
            userRepository.save(googleUser);
        }

        return googleUser;
    }

    // 4. 시큐리티 강제 로그인
    private Authentication securityLogin(User findUser) {

        UserDetails userDetails = new UserDetailsImpl(findUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    // 5. jwt 토큰 발급
    private void jwtToken(Authentication authentication, HttpServletResponse response) {
        UserDetailsImpl userDetailsImpl = ((UserDetailsImpl) authentication.getPrincipal());
        String token = JwtTokenUtils.generateJwtToken(userDetailsImpl);
        response.addHeader("Authorization", "BEARER" + " " + token);

    }
}

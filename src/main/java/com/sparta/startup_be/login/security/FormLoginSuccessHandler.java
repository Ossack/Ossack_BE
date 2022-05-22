package com.sparta.startup_be.login.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.startup_be.exception.StatusMessage;
import com.sparta.startup_be.login.security.jwt.JwtTokenUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 3번. 로그인 성공하면 여기로
public class FormLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "BEARER";

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) throws IOException {
        final UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());

        // 4번 호출. -> jwt util로 이동
        // Token 생성
        final String token = JwtTokenUtils.generateJwtToken(userDetails);

        // 토큰 헤더에 담아서 회원가입 끝
        response.addHeader(AUTH_HEADER, TOKEN_TYPE + " " + token);

        ObjectMapper objectMapper = new ObjectMapper();
//        HashMap<String,String > hashMap = new HashMap<>();
//        hashMap.put("nickName" ,userDetails.getUsername());

        StatusMessage message = new StatusMessage();
//        HttpHeaders headers= new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatusCode(StatusMessage.StatusEnum.OK);
        message.setMessage("로그인 완료");

        ResponseEntity responseEntity = new ResponseEntity<>(message, HttpStatus.OK);
        String msg = new String (objectMapper.writeValueAsString(responseEntity).getBytes("UTF-8"), "ISO-8859-1");

        response.getOutputStream()
                .println(msg);
    }

}

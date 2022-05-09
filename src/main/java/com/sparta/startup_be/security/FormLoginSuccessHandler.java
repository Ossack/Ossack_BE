package com.sparta.startup_be.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.startup_be.exception.StatusMessage;
import com.sparta.startup_be.security.jwt.JwtTokenUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

public class FormLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "BEARER";

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) throws IOException {
        final UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        // Token 생성
        final String token = JwtTokenUtils.generateJwtToken(userDetails);
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

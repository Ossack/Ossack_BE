package com.sparta.startup_be.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.startup_be.security.UserDetailsImpl;
import com.sparta.startup_be.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class HomeController {
    private final UserService userService;

//    @GetMapping("/")
//    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        if(userDetails == null) {
//            return "index";
//        }
//        model.addAttribute("username", userDetails.getUsername());
//        return "index";
//    }

    // 회원 로그인 페이지
//    @GetMapping("/user/login")
//    public String login() {
//        return "login";
//    }
//    @GetMapping("/user/loginView")
//        public String login() {
//        return "login";
//    }
//
//    // 회원 가입 페이지
//    @GetMapping("/user/signup")
//        public String signup() {
//        return "signup";
//    }

    @GetMapping("/user/kakao/callback")
    public String kakaologin(@RequestParam String code) throws JsonProcessingException {
        userService.kakaoLogin(code);
        return "redirect:/";
    }
}

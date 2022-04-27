package com.sparta.startup_be.service;

import com.sparta.startup_be.dto.ResultDto;
import com.sparta.startup_be.dto.SignupRequestDto;
import com.sparta.startup_be.model.User;
import com.sparta.startup_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.sparta.startup_be.exception.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    // 회원가입
    public ResultDto save(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String nickname = requestDto.getNickname();
        String message;
        Optional<User> foundUser = userRepository.findByUsername(username);
        Optional<User> foundNick = userRepository.findByNickname(nickname);

        // username 중복 체크
        if(validatedDuplicateUsername(foundUser)) {
            message = ILLEGAL_USER_NAME_DUPLICATION;
            return new ResultDto(message);
        }

        // nickname 중복 체크
        if(validatedDuplicateNickname(foundNick)) {
            message = ILLEGAL_NICK_NAME_DUPLICATION;
            return new ResultDto(message);
        }

        // 패스워드 암호화
        String password = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(username, nickname, password);
        userRepository.save(user);
        message = "회원가입 성공";
        return new ResultDto(message);
    }

    // 유저이름 중복 체크
    private boolean validatedDuplicateUsername(Optional<User> found) {
        return found.isPresent();
    }
    // 닉네임 중복 체크
    private boolean validatedDuplicateNickname(Optional<User> found) {
        return found.isPresent();
    }
}

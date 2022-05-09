package com.sparta.startup_be.login.service;

import com.sparta.startup_be.dto.ResultDto;
import com.sparta.startup_be.exception.StatusMessage;
import com.sparta.startup_be.login.dto.SignupRequestDto;
import com.sparta.startup_be.login.dto.UserRequestDto;
import com.sparta.startup_be.login.dto.UserResponseDto;
import com.sparta.startup_be.login.model.User;
import com.sparta.startup_be.login.repository.UserRepository;
import com.sparta.startup_be.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Optional;

import static com.sparta.startup_be.exception.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    // 회원가입
    public ResponseEntity<StatusMessage> save(SignupRequestDto requestDto) {
        String userEmail = requestDto.getUserEmail();
        String nickname = requestDto.getNickname();
        Optional<User> foundUser = userRepository.findByUserEmail(userEmail);
        Optional<User> foundNick = userRepository.findByNickname(nickname);

        StatusMessage message = new StatusMessage();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        // userEmail 중복 체크
        if(validatedDuplicateUserEmail(foundUser)) {
            message.setStatusCode(StatusMessage.StatusEnum.BAD_REQUEST);
            message.setMessage(ILLEGAL_USER_NAME_DUPLICATION);
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        }

        // nickname 중복 체크
        if(validatedDuplicateNickname(foundNick)) {
            message.setStatusCode(StatusMessage.StatusEnum.BAD_REQUEST);
            message.setMessage(ILLEGAL_NICK_NAME_DUPLICATION);
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        }

        String profile = "https://ossack.s3.ap-northeast-2.amazonaws.com/basicprofile.png";

        // 패스워드 암호화
        String password = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(userEmail, nickname, profile, password);
        userRepository.save(user);

        message.setStatusCode(StatusMessage.StatusEnum.OK);
        message.setMessage("회원가입 성공");

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    // 유저이름 중복 체크
    private boolean validatedDuplicateUserEmail(Optional<User> found) {
        return found.isPresent();
    }
    // 닉네임 중복 체크
    private boolean validatedDuplicateNickname(Optional<User> found) {
        return found.isPresent();
    }

    // 이메일 중복 확인
    public ResponseEntity<StatusMessage> dupEmail(UserRequestDto userDto) {
        StatusMessage message = new StatusMessage();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        if (userRepository.findByUserEmail(userDto.getUserEmail()).isPresent()) {

            message.setStatusCode(StatusMessage.StatusEnum.BAD_REQUEST);
            message.setMessage("이미 존재하는 이메일입니다.");

            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        }

        message.setStatusCode(StatusMessage.StatusEnum.OK);
        message.setMessage("사용할 수 있는 이메일입니다.");
        message.setData(userDto.getUserEmail());

        return new ResponseEntity<>(message, headers, HttpStatus.OK);

    }

    // 닉네임 중복 확인
    public ResponseEntity<StatusMessage> dupNick(UserRequestDto userDto) {
        StatusMessage message = new StatusMessage();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        if (userRepository.findByUserEmail(userDto.getNickname()).isPresent()) {

            message.setStatusCode(StatusMessage.StatusEnum.BAD_REQUEST);
            message.setMessage("이미 존재하는 닉네임입니다.");

            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        }

        message.setStatusCode(StatusMessage.StatusEnum.OK);
        message.setMessage("사용할 수 있는 닉네임입니다.");
        message.setData(userDto.getNickname());

        return new ResponseEntity<>(message, headers, HttpStatus.OK);

    }

}

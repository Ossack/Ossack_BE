package com.sparta.startup_be.login.service;

import com.sparta.startup_be.exception.StatusMessage;
import com.sparta.startup_be.login.dto.SignupRequestDto;
import com.sparta.startup_be.login.dto.UserRequestDto;
import com.sparta.startup_be.login.model.User;
import com.sparta.startup_be.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.sparta.startup_be.exception.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    // 회원가입
    public ResponseEntity<StatusMessage> signup(SignupRequestDto requestDto, BindingResult bindingResult) {
        StatusMessage message = new StatusMessage();
        HttpHeaders headers= new HttpHeaders();

        // 유효성 검증을 통해 유효하지 않은 결과를 JudgeSuccessDto에 담아서 클라이언트에게 보내준다.
        if (bindingResult.hasErrors()) {
            // 유효성 검사에 실패했을 경우 Error를 리스트 형식으로 가져온다.
            List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatusCode(StatusMessage.StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage(errors.get(0));

            return new ResponseEntity<>(message, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String userEmail = requestDto.getUserEmail();
        String nickname = requestDto.getNickname();
        Optional<User> foundUser = userRepository.findByUserEmail(userEmail);

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        // userEmail 중복 체크
        if(validatedDuplicateUserEmail(foundUser)) {
            message.setStatusCode(StatusMessage.StatusEnum.BAD_REQUEST);
            message.setMessage(ILLEGAL_USER_NAME_DUPLICATION);
            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
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
}

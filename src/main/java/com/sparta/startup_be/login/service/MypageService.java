package com.sparta.startup_be.login.service;

import com.sparta.startup_be.exception.StatusMessage;
import com.sparta.startup_be.login.dto.UserResponseDto;
import com.sparta.startup_be.login.model.User;
import com.sparta.startup_be.login.repository.UserRepository;
import com.sparta.startup_be.security.UserDetailsImpl;
import com.sparta.startup_be.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.Charset;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MypageService {
    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;

    // 프로필 이미지 수정
    @Transactional
    public ResponseEntity<StatusMessage> updateProfile(MultipartFile multipartFile, UserDetailsImpl userDetails){
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );

        // user repository에서 기존 이미지(파일명) 불러오기
        String oldImgName = user.getProfile();

        // 새로운 이미지 > S3 업로드 > 이미지 Url 생성
        String fileName = createFileName(multipartFile.getOriginalFilename());
        String imageUrl = s3Uploader.updateFile(multipartFile, oldImgName, fileName);
        User profile = new User(user.getId(), user.getUserEmail(), user.getNickname(), imageUrl);

        // 새로운 이미지 Url을 파싱해서 파일명으로만 DB에 저장
        String [] newImgUrl = imageUrl.split("/");
        String imageKey = newImgUrl[newImgUrl.length-1];
        user.update(user.getId(), imageKey);

        UserResponseDto userResponseDto = new UserResponseDto(profile);

        StatusMessage message = new StatusMessage();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatusCode(StatusMessage.StatusEnum.OK);
        message.setMessage("수정 완료");
        message.setData(userResponseDto);

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    // 이미지 파일명 변환 관련 메소드
    public String createFileName(String fileName) {
        // 먼저 파일 업로드 시, 파일명을 난수화하기 위해 random으로 돌립니다.
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        // file 형식이 잘못된 경우를 확인하기 위해 만들어진 로직이며,
        // 파일 타입과 상관없이 업로드할 수 있게 하기 위해 .의 존재 유무만 판단
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }

    }

    // 회원 정보 조회
    public ResponseEntity<StatusMessage> isLogin(UserDetailsImpl userDetails) {
        String basicImg = "https://ossack.s3.ap-northeast-2.amazonaws.com/basicprofile.png";
        String profileImg = userDetails.getUser().getProfile();

        // 기본 프로필 이외에
        if (!profileImg.equals(basicImg)) {
            profileImg = "https://ossack.s3.ap-northeast-2.amazonaws.com/" + profileImg;
        }
        UserResponseDto userResponseDto = new UserResponseDto(userDetails.getUser(), profileImg);

        StatusMessage message = new StatusMessage();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatusCode(StatusMessage.StatusEnum.OK);
        message.setMessage("유저 정보 조회");
        message.setData(userResponseDto);

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

}

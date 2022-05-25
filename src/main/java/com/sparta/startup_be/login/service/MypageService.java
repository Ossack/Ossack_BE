package com.sparta.startup_be.login.service;

import com.sparta.startup_be.login.dto.UserResponseDto;
import com.sparta.startup_be.login.model.User;
import com.sparta.startup_be.login.repository.UserRepository;
import com.sparta.startup_be.login.security.UserDetailsImpl;
import com.sparta.startup_be.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MypageService {
    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;

    // 프로필 사진만 수정
    @Transactional
    public void updateProfile(MultipartFile multipartFile, String nickname, UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );

        // user repository에서 기존 이미지(파일명) 불러오기
        String oldImgName = user.getProfile();

        // 새로운 이미지 > S3 업로드 > 이미지 Url 생성
        String fileName = createFileName(multipartFile.getOriginalFilename());
        String imageUrl = s3Uploader.updateFile(multipartFile, oldImgName, fileName);

        // 새로운 이미지 Url을 파싱해서 파일명으로만 DB에 저장
        String[] newImgUrl = imageUrl.split("/");
        String imageKey = newImgUrl[newImgUrl.length - 1];
        user.update(user.getId(), nickname, imageKey);
    }

    // 닉네임만 수정 또는 프로필 사진 삭제
    @Transactional
    public void deleteImg(String profileImgUrl, String nickname, UserDetailsImpl userDetails) {
        String defaultImg = "https://ossack.s3.ap-northeast-2.amazonaws.com/basicprofile.png";
        User user = userRepository.findById(userDetails.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 없습니다")
        );

        // 사진 삭제시 profileUrl이 안들어옴 -> 기본이미지로 변경
        if (profileImgUrl == null || profileImgUrl.equals("")) {

            // user에서 기존 이미지(파일명) 불러오기
            String oldImg = user.getProfile();
            s3Uploader.deleteImage(oldImg);

            // 디폴트 이미지
            user.update(user.getId(), nickname, defaultImg);
        } // 사진 유지 profileUrl이 들어옴 -> 그러면 그대로 해줘
        else {
            user.update(user.getId(), nickname, profileImgUrl.split("/")[profileImgUrl.split("/").length-1]);
        }
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
    public ResponseEntity<UserResponseDto> isLogin(UserDetailsImpl userDetails) {
        String basicImg = "https://ossack.s3.ap-northeast-2.amazonaws.com/basicprofile.png";

        String profileImg = userDetails.getUser().getProfile();

        // 기본 프로필 이외의 프로필일 때 주소 추가
        if (!profileImg.equals(basicImg)) {
            profileImg = "https://ossack.s3.ap-northeast-2.amazonaws.com/" + profileImg;
        }
        UserResponseDto userResponseDto = new UserResponseDto(userDetails.getUser(), profileImg);

        return ResponseEntity.status(200).body(userResponseDto);
    }

    // 회원 탈퇴
    public ResponseEntity<UserResponseDto> withdrawUser(UserDetailsImpl userDetails){
        User user = userRepository.findById(userDetails.getId()).orElseThrow(
                ()-> new IllegalArgumentException("등록되지 않은 회원입니다.")
        );
        user.delete();
        return ResponseEntity.status(200).body(new UserResponseDto(user));
    }
}

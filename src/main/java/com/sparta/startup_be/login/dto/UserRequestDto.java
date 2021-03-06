package com.sparta.startup_be.login.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class UserRequestDto {
    @NotBlank
    @Size
    @Pattern(regexp = "([\\w-.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$", message = "이메일 형식이 맞지 않습니다.")
    private String userEmail;

    @NotBlank(message = "닉네임을 입력해 주세요")
    @Size(min = 2, max = 10, message = "2자 이상 12자 이하의 값을 입력해 주세요!")
    @Pattern(regexp = "^[가-힣a-zA-Z]{2,10}$", message = "닉네임은 한글과 영문만 입력해 주세요!")
    private String nickname;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$", message = "영문/숫자/특수문자(!@#$%^&*)를 포함하여 8~16자로 입력해야 합니다")
    private String password;
}
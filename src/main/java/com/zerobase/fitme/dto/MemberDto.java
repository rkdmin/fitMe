package com.zerobase.fitme.dto;

import com.zerobase.fitme.entity.Member;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

public class MemberDto {
    @Data
    @Builder
    public static class SignIn{
        @NotBlank(message = "아이디를 입력하세요.")
        private String username;

        @NotBlank(message = "비밀번호를 입력하세요.")
        private String password;
    }

    @Data
    @Builder
    public static class SignUp{
        @NotBlank(message = "아이디를 입력하세요.")
        private String username;
        @NotBlank(message = "비밀번호를 입력하세요.")
        @Pattern(regexp="^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,16}$", message = "비밀번호는 영어, 숫자, 특수문자 포함해서 8~16자리 이내로 입력해주세요.")
        private String password;

        @NotBlank(message = "이메일을 입력하세요.")
        @Email(message = "이메일 형식이 잘못되었습니다.")
        private String email;

        @NotBlank(message = "권한을 설정해주세요.")
        private String roles;

        public Member toEntity(){
            return Member.builder()
                    .username(this.username)
                    .password(this.password)
                    .roles(this.roles)
                    .build();
        }
    }
}

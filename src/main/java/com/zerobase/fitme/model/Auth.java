package com.zerobase.fitme.model;

import com.zerobase.fitme.entity.Member;
import lombok.Data;

public class Auth {
    @Data
    public static class SignIn{
        private String username;
        private String password;
    }

    @Data
    public static class SignUp{
        private String username;
        private String password;
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

package com.zerobase.fitme.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

public class UdtSeller {
    @Data
    public static class Request{
        private Long id;
        private String companyName;
        private String sellerName;
        private String address;
        private String phone;
        private String businessNumber;

        @Email(message = "이메일 형식에 맞춰서 입력하세요")
        private String email;
    }
}

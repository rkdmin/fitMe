package com.zerobase.fitme.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

public class RegSeller {
    @Data
    public static class Request{
        @NotBlank(message = "회사 이름을 입력하세요")
        private String companyName;

        @NotBlank(message = "대표자 이름을 입력하세요")
        private String sellerName;

        @NotBlank(message = "주소를 입력하세요")
        private String address;

        @NotBlank(message = "연락처를 입력하세요")
        private String phone;

        @NotBlank(message = "사업자등록번호를 입력하세요")
        private String businessNumber;

        @Email(message = "이메일 형식에 맞춰서 입력하세요")
        @NotBlank(message = "이메일을 입력하세요")
        private String email;
    }
}

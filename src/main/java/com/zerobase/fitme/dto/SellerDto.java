package com.zerobase.fitme.dto;

import com.zerobase.fitme.entity.Seller;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

public class SellerDto {
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

    @Data
    @Builder
    public static class Response{
        private String companyName;
        private String sellerName;
        private String address;
        private String phone;
        private String businessNumber;
        private String email;

        public static Response toDto(Seller seller) {
            return Response.builder()
                .companyName(seller.getCompanyName())
                .sellerName(seller.getSellerName())
                .address(seller.getAddress())
                .phone(seller.getPhone())
                .businessNumber(seller.getBusinessNumber())
                .email(seller.getEmail())
                .build();
        }
    }
}

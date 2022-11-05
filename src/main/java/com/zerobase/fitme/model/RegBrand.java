package com.zerobase.fitme.model;

import javax.validation.constraints.NotBlank;
import lombok.Data;

public class RegBrand {
    @Data
    public static class Request{
        @NotBlank(message = "브랜드명을 입력하세요")
        private String brandName;

        @NotBlank(message = "주소를 입력하세요")
        private String address;

        @NotBlank(message = "연락처를 입력하세요")
        private String phone;
    }
}

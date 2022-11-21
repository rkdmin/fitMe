package com.zerobase.fitme.dto;

import com.zerobase.fitme.entity.Brand;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

public class BrandDto {
    @Data
    public static class Request{
        @NotBlank(message = "브랜드명을 입력하세요")
        private String brandName;

        @NotBlank(message = "URL을 입력하세요")
        private String url;

        @NotBlank(message = "연락처를 입력하세요")
        private String phone;
    }

    @Data
    @Builder
    public static class Response{
        private String brandName;
        private String url;
        private String phone;

        public static BrandDto.Response toDto(Brand brand) {
            return Response.builder()
                .brandName(brand.getBrandName())
                .url(brand.getUrl())
                .phone(brand.getPhone())
                .build();
        }
    }


}

package com.zerobase.fitme.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

public class CategoryDto {
    @Data
    public static class Request{
        @NotBlank(message = "카테고리명을 입력하세요")
        private String categoryName;
    }
}

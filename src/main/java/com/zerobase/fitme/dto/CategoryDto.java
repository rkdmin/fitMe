package com.zerobase.fitme.dto;

import com.zerobase.fitme.entity.Category;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

public class CategoryDto {
    @Data
    public static class Request{
        @NotBlank(message = "카테고리명을 입력하세요")
        private String categoryName;
    }

    @Data
    @Builder
    public static class Response{
        private Long id;
        private String categoryName;
        public static Response toDto(Category category){
            return Response.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .build();
        }
    }
}

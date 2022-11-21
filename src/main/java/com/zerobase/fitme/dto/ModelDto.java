package com.zerobase.fitme.dto;

import com.zerobase.fitme.entity.Model;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

public class ModelDto {
    @Data
    public static class Request{
        @NotNull(message = "키를 입력하세요")
        private Integer height;

        @NotNull(message = "상의 사이즈를 입력하세요")
        private Integer topSize;

        @NotNull(message = "하의 사이즈를 입력하세요")
        private Integer bottomSize;

        @NotNull(message = "신발 사이즈를 입력하세요")
        private Integer shoesSize;

        @NotBlank(message = "모델 이름을 입력하세요")
        private String modelName;
    }

    @Data
    @Builder
    public static class Response{
        private Integer height;
        private Integer topSize;
        private Integer bottomSize;
        private Integer shoesSize;
        private String modelName;

        public static Response toDto(Model model) {
            return Response.builder()
                .height(model.getHeight())
                .topSize(model.getTopSize())
                .bottomSize(model.getBottomSize())
                .shoesSize(model.getShoesSize())
                .modelName(model.getModelName())
                .build();
        }
    }
}

package com.zerobase.fitme.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

public class RegModel {
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
}

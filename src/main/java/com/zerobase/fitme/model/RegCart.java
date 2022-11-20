package com.zerobase.fitme.model;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

public class RegCart {
    @Data
    public static class Request{
        @NotNull(message = "사이즈를 입력하세요")
        private String size;
        @NotNull(message = "색상을 입력하세요")
        private String color;
    }
}

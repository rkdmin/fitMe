package com.zerobase.fitme.model;

import com.zerobase.fitme.type.ColorType;
import com.zerobase.fitme.type.SizeType;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

public class RegItemInfo {
    @Data
    public static class Request{
        // 상품 정보
        @NotBlank(message = "소재를 입력하세요")
        private String material;

        @NotBlank(message = "색상을 입력하세요")
        private List<ColorType> colorList;

        @NotBlank(message = "사이즈를 입력하세요")
        private List<SizeType> sizeList;
    }
}

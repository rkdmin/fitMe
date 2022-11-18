package com.zerobase.fitme.dto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ItemInfoDto {
        // 상품 정보
        @NotBlank(message = "소재를 입력하세요")
        private String material;

        @NotBlank(message = "색상을 입력하세요")
        private List<String> colorList;

        @NotBlank(message = "사이즈를 입력하세요")
        private List<String> sizeList;
}

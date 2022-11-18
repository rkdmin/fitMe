package com.zerobase.fitme.model;

import com.zerobase.fitme.type.ColorType;
import com.zerobase.fitme.type.SizeType;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegItemInfo {
        // 상품 정보
        @NotBlank(message = "소재를 입력하세요")
        private String material;

        @NotBlank(message = "색상을 입력하세요")
        private List<String> colorList;

        @NotBlank(message = "사이즈를 입력하세요")
        private List<String> sizeList;
}

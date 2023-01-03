package com.zerobase.fitme.dto;

import com.zerobase.fitme.entity.ItemInfo;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemInfoDto {
        // 상품 정보
        @NotBlank(message = "소재를 입력하세요")
        private String material;

        @NotNull(message = "색상을 입력하세요")
        @Min(value = 1, message = "색상을 입력하세요")
        private List<String> colorList;

        @NotNull(message = "사이즈를 입력하세요")
        @Min(value = 1, message = "사이즈를 입력하세요")
        private List<String> sizeList;

        public static ItemInfoDto toDto(ItemInfo itemInfo) {
                return ItemInfoDto.builder()
                    .material(itemInfo.getMaterial())
                    .colorList(itemInfo.getColorList().stream().map(x -> x.toString()).collect(Collectors.toList()))
                    .sizeList(itemInfo.getSizeList().stream().map(x -> x.toString()).collect(Collectors.toList()))
                    .build();
        }
}

package com.zerobase.fitme.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

public class RegItem {
    @Data
    public static class Request{
        @NotBlank(message = "상품명을 입력하세요")
        private String itemName;

        @NotBlank(message = "이미지 경로를 입력하세요")
        private String url;

        @NotNull(message = "정가를 입력하세요")
        private Long price;

        @NotNull(message = "할인율을 입력하세요(%)")
        private Long saleRate;

        @NotBlank(message = "내용을 입력하세요")
        private String content;

        @NotNull(message = "재고를 입력하세요")
        @Min(value = 1, message = "재고는 최소 1개 이상입니다.")
        private Long cnt;

        @NotNull(message = "브랜드 번호를 입력하세요")
        private Long brandId;

        @NotNull(message = "판매자 번호를 입력하세요")
        private Long sellerId;

        @NotNull(message = "모델 번호를 입력하세요")
        private Long modelId;

        // 상품 정보
        private RegItemInfo regItemInfo;
    }
}

package com.zerobase.fitme.dto;

import com.zerobase.fitme.entity.Item;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

public class ItemDto {
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

        @NotNull
        private ItemInfoDto regItemInfo;

        @NotNull
        private List<String> categoryNameList;
    }

    @Data
    @Builder
    public static class Response {
        private Long id;
        private String itemName;
        private String url;
        private Long price;
        private Long saleRate;
        private Long salePrice;
        private Long cnt;

        public static List<Response> toDtoList(List<Item> itemList) {
            List<Response> itemDtoList = new ArrayList<>();
            for(Item item: itemList){
                itemDtoList.add(toDto(item));
            }

            return itemDtoList;
        }
        public static Response toDto(Item item) {
            return Response.builder()
                .id(item.getId())
                .itemName(item.getItemName())
                .url(item.getUrl())
                .price(item.getPrice())
                .saleRate(item.getSaleRate())
                .salePrice(item.getSalePrice())
                .cnt(item.getCnt())
                .build();
        }
    }

    @Data
    @Builder
    public static class ResponseDetail {
        private Long id;
        private String itemName;
        private String url;
        private Long price;
        private Long saleRate;
        private Long salePrice;
        private Long cnt;

        // 추가
        private String content;
        private long view;
        private LocalDateTime regDt;
        private ItemInfoDto itemInfoDto;
        private BrandDto.Response brandDto;
        private SellerDto.Response sellerDto;
        private ModelDto.Response modelDto;
        private List<String> categoryNameList;

        public static ResponseDetail toDto(Item item) {
            return ResponseDetail.builder()
                .id(item.getId())
                .itemName(item.getItemName())
                .url(item.getUrl())
                .price(item.getPrice())
                .saleRate(item.getSaleRate())
                .salePrice(item.getSalePrice())
                .cnt(item.getCnt())
                .content(item.getContent())
                .view(item.getView())
                .regDt(item.getRegDt())
                .itemInfoDto(ItemInfoDto.toDto(item.getItemInfo()))
                .brandDto(BrandDto.Response.toDto(item.getBrand()))
                .sellerDto(SellerDto.Response.toDto(item.getSeller()))
                .modelDto(ModelDto.Response.toDto(item.getModel()))
                // itemCategoryList -> categoryNameList
                .categoryNameList(item.getItemCategoryList().stream()
                    .map(x -> x.getCategory().getCategoryName())
                    .collect(Collectors.toList()))
                .build();
        }
    }

}

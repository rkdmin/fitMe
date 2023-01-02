package com.zerobase.fitme.dto;

import com.zerobase.fitme.entity.Cart;
import com.zerobase.fitme.entity.Item;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

public class CartDto {
    @Data
    @Builder
    public static class Request{
        @NotNull(message = "사이즈를 입력하세요")
        private String size;
        @NotNull(message = "색상을 입력하세요")
        private String color;
    }

    @Data
    public static class RequestPatch{
        private String size;
        private String color;
    }

    @Data
    @Builder
    public static class Response {
        private Long id;
        private ItemDto.Response itemDto;
        private String color;
        private String size;

        public static Response toDto(Cart cart) {
            return Response.builder()
                .id(cart.getId())
                .itemDto(ItemDto.Response.toDto(cart.getItem()))
                .color(cart.getColor().name())
                .size(cart.getSize().name())
                .build();
        }
    }
}

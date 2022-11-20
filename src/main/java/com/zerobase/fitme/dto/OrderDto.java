package com.zerobase.fitme.dto;

import com.zerobase.fitme.entity.Order;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

public class OrderDto {
    @Data
    @Builder
    public static class Response {
        private Long id;
        private ItemDto.Response itemDto;
        private String orderStatus;
        private LocalDateTime regDt;

        public static Response toDto(Order order) {
            return Response.builder()
                .id(order.getId())
                .itemDto(ItemDto.Response.toDto(order.getItem()))
                .orderStatus(order.getOrderStatus().name())
                .regDt(order.getRegDt())
                .build();
        }
    }
}

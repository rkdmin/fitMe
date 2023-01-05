package com.zerobase.fitme.dto;

import com.zerobase.fitme.entity.Order;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class OrderDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RequestPatch {
        @NotBlank(message = "주문 상태를 입력해주세요")
        private String orderStatus;
    }

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

    @Data
    @Builder
    public static class ResponseManager {
        private Long id;
        private ItemDto.Response itemDto;
        private String orderStatus;
        private LocalDateTime regDt;

        // 멤버정보
        private Long memberId;
        private String username;

        public static ResponseManager toDto(Order order) {
            return ResponseManager.builder()
                .id(order.getId())
                .itemDto(ItemDto.Response.toDto(order.getItem()))
                .orderStatus(order.getOrderStatus().name())
                .regDt(order.getRegDt())
                .memberId(order.getMember().getId())
                .username(order.getMember().getUsername())
                .build();
        }
    }


}

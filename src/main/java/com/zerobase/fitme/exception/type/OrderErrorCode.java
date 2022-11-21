package com.zerobase.fitme.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderErrorCode {
    INVALID_REQUEST("잘못된 요청입니다."),
    ORDER_NOT_FOUND("해당 주문을 찾을 수 없습니다."),
    OUT_OF_STOCK("재고가 소진되었습니다."),
    ALREADY_START_ITEM("이미 배송이 시작되었거나 구매가 완료된 상품입니다.")
    ;

    private final String description;
}

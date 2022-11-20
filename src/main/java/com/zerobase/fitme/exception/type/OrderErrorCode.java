package com.zerobase.fitme.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderErrorCode {
    INVALID_REQUEST("잘못된 요청입니다."),
    ORDER_NOT_FOUND("해당 주문을 찾을 수 없습니다."),
    OUT_OF_STOCK("재고가 소진되었습니다.")
    ;

    private final String description;
}

package com.zerobase.fitme.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SellerErrorCode {
    INVALID_REQUEST("잘못된 요청입니다."),
    SELLER_NOT_FOUND("해당 판매자를 찾을 수 없습니다.")
    ;

    private final String description;
}

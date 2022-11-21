package com.zerobase.fitme.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ItemErrorCode {
    INVALID_REQUEST("잘못된 요청입니다."),
    ALREADY_EXIST_ITEM_NAME("이미 존재하는 상품이름입니다."),
    ITEM_NOT_FOUND("해당 상품을 찾을 수 없습니다.")
    ;

    private final String description;
}

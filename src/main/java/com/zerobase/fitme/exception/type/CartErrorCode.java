package com.zerobase.fitme.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CartErrorCode {
    INVALID_REQUEST("잘못된 요청입니다."),
    EMPTY_COLOR_OR_SIZE("사이즈 또는 색상이 상품에 없습니다.")
    ;

    private final String description;
}

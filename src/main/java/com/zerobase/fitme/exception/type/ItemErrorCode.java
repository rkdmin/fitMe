package com.zerobase.fitme.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ItemErrorCode {
    INVALID_REQUEST("잘못된 요청입니다.")
    ;

    private final String description;
}

package com.zerobase.fitme.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BrandErrorCode {
    INVALID_REQUEST("잘못된 요청입니다."),
    ALREADY_EXIST_BRAND_NAME("이미 존재하는 브랜드입니다."),
    BRAND_NOT_FOUND("해당 브랜드를 찾을 수 없습니다.")
    ;

    private final String description;
}

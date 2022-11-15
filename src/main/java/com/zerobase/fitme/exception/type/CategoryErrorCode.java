package com.zerobase.fitme.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryErrorCode {
    CATEGORY_NOT_FOUND("해당 카테고리를 찾을 수 없습니다."),
    ALREADY_EXIST_CATEGORY_NAME("이미 존재하는 카테고리명 입니다."),
    INVALID_REQUEST("잘못된 요청입니다.")
    ;

    private final String description;
}

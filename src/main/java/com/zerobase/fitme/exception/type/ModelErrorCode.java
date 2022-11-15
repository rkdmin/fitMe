package com.zerobase.fitme.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ModelErrorCode {
    INVALID_REQUEST("잘못된 요청입니다."),
    MODEL_NOT_FOUND("해당 모델을 찾을 수 없습니다.")
    ;

    private final String description;
}

package com.zerobase.fitme.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR("내부 서버 오류가 발생했습니다."),
    USER_NOT_FOUND("해당 유저를 찾을 수 없습니다."),
    ALREADY_EXIST_ID("이미 존재하는 아이디 입니다.")
    ;

    private final String description;
}

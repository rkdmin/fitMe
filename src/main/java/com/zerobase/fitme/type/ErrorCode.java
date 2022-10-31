package com.zerobase.fitme.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR("내부 서버 오류가 발생했습니다."),
    USER_NOT_FOUND("해당 유저를 찾을 수 없습니다."),
    ALREADY_EXIST_ID("이미 존재하는 아이디 입니다."),
    LOGIN_FAIL("아이디 혹은 비밀번호가 일치하지 않습니다."),
    WRONG_ROLES("잘못된 권한입니다."),
    INVALID_EMAIL_KEY("유효한 이메일 키가 아닙니다.");

    private final String description;
}

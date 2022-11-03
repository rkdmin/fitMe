package com.zerobase.fitme.exception;

import static com.zerobase.fitme.type.ErrorCode.INTERNAL_SERVER_ERROR;

import com.zerobase.fitme.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberException.class)
    public ErrorResponse handelMemberException(MemberException e){
        log.error("{} is occurred", e.getErrorMassage());

        return new ErrorResponse(e.getErrorCode(), e.getErrorMassage());
    }

//     다 거르고 마지막 예외
    @ExceptionHandler(Exception.class)
    public ErrorResponse handelException(Exception e){
        log.error("{} is occurred", e);

        return new ErrorResponse(INTERNAL_SERVER_ERROR,
                INTERNAL_SERVER_ERROR.getDescription());
    }
}

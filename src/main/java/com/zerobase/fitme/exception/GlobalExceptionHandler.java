package com.zerobase.fitme.exception;

import static com.zerobase.fitme.exception.type.ErrorCode.INTERNAL_SERVER_ERROR;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.BadRequest;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ErrorResponse> handelMemberException(MemberException e){
        log.error("{} is occurred", e.getErrorMassage());

        return new ResponseEntity(new ErrorResponse(e.getErrorCode().toString(), e.getErrorMassage()),
            HttpStatus.resolve(HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(CategoryException.class)
    public ResponseEntity<ErrorResponse> handelCategoryException(CategoryException e){
        log.error("{} is occurred", e.getErrorMassage());

        return new ResponseEntity(new ErrorResponse(e.getErrorCode().toString(), e.getErrorMassage()),
            HttpStatus.resolve(HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(BrandException.class)
    public ResponseEntity<ErrorResponse> handelBrandException(BrandException e){
        log.error("{} is occurred", e.getErrorMassage());

        return new ResponseEntity(new ErrorResponse(e.getErrorCode().toString(), e.getErrorMassage()),
            HttpStatus.resolve(HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(ModelException.class)
    public ResponseEntity<ErrorResponse> handelModelException(ModelException e){
        log.error("{} is occurred", e.getErrorMassage());

        return new ResponseEntity(new ErrorResponse(e.getErrorCode().toString(), e.getErrorMassage()),
            HttpStatus.resolve(HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(SellerException.class)
    public ResponseEntity<ErrorResponse> handelSellerException(SellerException e){
        log.error("{} is occurred", e.getErrorMassage());

        return new ResponseEntity(new ErrorResponse(e.getErrorCode().toString(), e.getErrorMassage()),
            HttpStatus.resolve(HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(ItemException.class)
    public ResponseEntity<ErrorResponse> handelItemException(ItemException e){
        log.error("{} is occurred", e.getErrorMassage());

        return new ResponseEntity(new ErrorResponse(e.getErrorCode().toString(), e.getErrorMassage()),
            HttpStatus.resolve(HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(ItemInfoException.class)
    public ResponseEntity<ErrorResponse> handelItemInfoException(ItemInfoException e){
        log.error("{} is occurred", e.getErrorMassage());

        return new ResponseEntity(new ErrorResponse(e.getErrorCode().toString(), e.getErrorMassage()),
            HttpStatus.resolve(HttpStatus.BAD_REQUEST.value()));
    }

//     다 거르고 마지막 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handelException(Exception e){
        log.error("{} is occurred", e);

        return new ResponseEntity(
            new ErrorResponse(INTERNAL_SERVER_ERROR.toString(), INTERNAL_SERVER_ERROR.getDescription()),
            HttpStatus.resolve(HttpStatus.BAD_REQUEST.value())
        );
    }
}

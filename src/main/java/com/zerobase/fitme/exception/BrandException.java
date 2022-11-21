package com.zerobase.fitme.exception;



import com.zerobase.fitme.exception.type.BrandErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandException extends RuntimeException{
    // 이런식으로해야 에러 클래스를 여러개 안만들어도댐
    private BrandErrorCode errorCode;
    private String errorMassage;

    public BrandException(BrandErrorCode errorCode){
        this.errorCode = errorCode;
        this.errorMassage = errorCode.getDescription();
    }
}

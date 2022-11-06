package com.zerobase.fitme.controller;

import static com.zerobase.fitme.type.ErrorCode.INTERNAL_SERVER_ERROR;

import com.zerobase.fitme.entity.Brand;
import com.zerobase.fitme.entity.Seller;
import com.zerobase.fitme.exception.BrandException;
import com.zerobase.fitme.exception.SellerException;
import com.zerobase.fitme.model.RegBrand;
import com.zerobase.fitme.model.RegSeller;
import com.zerobase.fitme.model.UdtBrand;
import com.zerobase.fitme.model.UdtSeller;
import com.zerobase.fitme.service.BrandService;
import com.zerobase.fitme.service.SellerService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin/seller")
@RequiredArgsConstructor
public class SellerController {
    private final SellerService sellerService;

    @PreAuthorize("hasRole('ADMIN')") // 관리자만 접속 가능
    @PostMapping("/register")
    public String register(@RequestBody @Valid RegSeller.Request request, BindingResult bindingResult){
        // @valid 발생
        validation(bindingResult);

        sellerService.register(request);

        return "판매자 등록이 완료되었습니다.";
    }

    @PreAuthorize("hasRole('ADMIN')") // 관리자만 접속 가능
    @GetMapping("")
    public List<Seller> read(){
        return sellerService.read();
    }

    @PreAuthorize("hasRole('ADMIN')") // 관리자만 접속 가능
    @PatchMapping("/edit/{id}")
    public Seller patch(@RequestBody @Valid UdtSeller.Request request, BindingResult bindingResult,
        @PathVariable Long id){

        // @valid 발생
        validation(bindingResult);

        return sellerService.patch(request, id);
    }

    @PreAuthorize("hasRole('ADMIN')") // 관리자만 접속 가능
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        sellerService.delete(id);
        return "판매자 삭제가 완료되었습니다.";
    }

    private static void validation(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            throw new SellerException(INTERNAL_SERVER_ERROR, list.get(0).getDefaultMessage());
        }
    }
}

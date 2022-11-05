package com.zerobase.fitme.controller;

import static com.zerobase.fitme.type.ErrorCode.INTERNAL_SERVER_ERROR;

import com.zerobase.fitme.entity.Category;
import com.zerobase.fitme.exception.BrandException;
import com.zerobase.fitme.exception.MemberException;
import com.zerobase.fitme.model.RegBrand;
import com.zerobase.fitme.model.RegCategory;
import com.zerobase.fitme.model.UdtCategory;
import com.zerobase.fitme.service.BrandService;
import com.zerobase.fitme.service.CategoryService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin/brand")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @PreAuthorize("hasRole('ADMIN')") // 관리자만 접속 가능
    @PostMapping("/register")
    public String register(@RequestBody @Valid RegBrand.Request request, BindingResult bindingResult){
        // @valid 발생
        validation(bindingResult);

        brandService.register(request);

        return "브랜드 등록이 완료되었습니다.";
    }

    private static void validation(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            throw new BrandException(INTERNAL_SERVER_ERROR, list.get(0).getDefaultMessage().toString());
        }
    }
}

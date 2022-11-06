package com.zerobase.fitme.controller;

import static com.zerobase.fitme.type.ErrorCode.INTERNAL_SERVER_ERROR;

import com.zerobase.fitme.entity.Brand;
import com.zerobase.fitme.exception.BrandException;
import com.zerobase.fitme.exception.ModelException;
import com.zerobase.fitme.model.RegBrand;
import com.zerobase.fitme.model.RegModel;
import com.zerobase.fitme.model.UdtBrand;
import com.zerobase.fitme.service.BrandService;
import com.zerobase.fitme.service.ModelService;
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
@RequestMapping("/admin-manager/model")
@RequiredArgsConstructor
public class ModelController {
    private final ModelService modelService;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")// 관리자 매니저만 접속가능
    @PostMapping("/register")
    public String register(@RequestBody @Valid RegModel.Request request, BindingResult bindingResult){
        // @valid 발생
        validation(bindingResult);

        modelService.register(request);

        return "모델 등록이 완료되었습니다.";
    }

//    @PreAuthorize("hasRole('ADMIN')") // 관리자만 접속 가능
//    @GetMapping("")
//    public List<Brand> read(){
//        return brandService.read();
//    }
//
//    @PreAuthorize("hasRole('ADMIN')") // 관리자만 접속 가능
//    @PatchMapping("/edit")
//    public Brand patch(@RequestBody UdtBrand.Request request){
//
//        return brandService.patch(request);
//    }
//
//    @PreAuthorize("hasRole('ADMIN')") // 관리자만 접속 가능
//    @DeleteMapping("/delete/{id}")
//    public String patch(@PathVariable Long id){
//        brandService.delete(id);
//        return "브랜드 삭제가 완료되었습니다.";
//    }

    private static void validation(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            throw new ModelException(INTERNAL_SERVER_ERROR, list.get(0).getDefaultMessage());
        }
    }
}

package com.zerobase.fitme.controller;

import com.zerobase.fitme.dto.CategoryDto;
import com.zerobase.fitme.exception.MemberException;
import com.zerobase.fitme.exception.type.MemberErrorCode;
import com.zerobase.fitme.model.UdtCategory;
import com.zerobase.fitme.service.CategoryService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')") // 관리자만 접속 가능
    @PostMapping("/admin")
    public ResponseEntity<String> register(@RequestBody @Valid CategoryDto.Request request, BindingResult bindingResult){
        // @valid 발생
        validation(bindingResult);

        categoryService.register(request);

        return ResponseEntity.ok("카테고리 등록이 완료되었습니다.");
    }

    @PreAuthorize("hasRole('ADMIN')") // 관리자만 접속 가능
    @PatchMapping("/admin")
    public ResponseEntity<String> patch(@RequestBody UdtCategory.Request request){

        categoryService.patch(request);

        return ResponseEntity.ok("카테고리 수정이 완료되었습니다.");
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER')")// 모두 접속가능
    @GetMapping("")
    public ResponseEntity<List<CategoryDto.Response>> read(){
        return ResponseEntity.ok(categoryService.read());
    }

    private static void validation(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            throw new MemberException(
                MemberErrorCode.INVALID_REQUEST, list.get(0).getDefaultMessage().toString());
        }
    }
}

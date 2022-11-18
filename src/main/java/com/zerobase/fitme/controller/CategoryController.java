package com.zerobase.fitme.controller;

import static com.zerobase.fitme.type.ErrorCode.INTERNAL_SERVER_ERROR;

import com.zerobase.fitme.entity.Category;
import com.zerobase.fitme.exception.MemberException;
import com.zerobase.fitme.model.RegCategory;
import com.zerobase.fitme.model.UdtCategory;
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
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')") // 관리자만 접속 가능
    @PostMapping("/register")
    public String register(@RequestBody @Valid RegCategory.Request request, BindingResult bindingResult){
        // @valid 발생
        validation(bindingResult);

        categoryService.register(request);

        return "카테고리 등록이 완료되었습니다.";
    }

    @PreAuthorize("hasRole('ADMIN')") // 관리자만 접속 가능
    @PatchMapping("/edit")
    public String patch(@RequestBody UdtCategory.Request request){

        categoryService.patch(request);

        return "카테고리 수정이 완료되었습니다.";
    }

    @PreAuthorize("hasRole('ADMIN')") // 관리자만 접속 가능
    @GetMapping("")
    public List<Category> read(){
        return categoryService.read();
    }

    private static void validation(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            throw new MemberException(INTERNAL_SERVER_ERROR, list.get(0).getDefaultMessage().toString());
        }
    }
}

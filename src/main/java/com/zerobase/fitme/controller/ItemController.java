package com.zerobase.fitme.controller;

import static com.zerobase.fitme.exception.type.ItemErrorCode.INVALID_REQUEST;

import com.zerobase.fitme.dto.ItemDto;
import com.zerobase.fitme.entity.Item;
import com.zerobase.fitme.exception.ItemException;
import com.zerobase.fitme.service.ItemCategoryService;
import com.zerobase.fitme.service.ItemService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")// 관리자, 매니저만 접속가능
    @PostMapping("/register/admin-manager")
    public ResponseEntity<String> register(@RequestBody @Valid ItemDto.Request request, BindingResult bindingResult){
        // @valid 발생
        validation(bindingResult);

        itemService.register(request);

        return ResponseEntity.ok("상품 등록이 완료되었습니다.");
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER', 'USER')")// 모두 접속가능
    @GetMapping("/top20")
    public ResponseEntity<List<ItemDto.Response>> readTop20(){
        return ResponseEntity.ok(itemService.readTop20());
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER', 'USER')")// 모두 접속가능
    @GetMapping("/brand/{brandName}")
    public ResponseEntity<Page<ItemDto.Response>> readByBrandName(@PathVariable String brandName, final Pageable pageable){
        return ResponseEntity.ok(itemService.readByBrandName(brandName, pageable));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER', 'USER')")// 모두 접속가능
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<Page<ItemDto.Response>> readByCategoryName(@PathVariable String categoryName, final Pageable pageable){
        return ResponseEntity.ok(itemService.readByCategoryName(categoryName, pageable));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER', 'USER')")// 모두 접속가능
    @GetMapping("/detail/{itemId}")
    public ResponseEntity<ItemDto.ResponseDetail> readDetail(@PathVariable Long itemId){
        return ResponseEntity.ok(itemService.readDetail(itemId));
    }


    private static void validation(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            throw new ItemException(INVALID_REQUEST, list.get(0).getDefaultMessage());
        }
    }
}

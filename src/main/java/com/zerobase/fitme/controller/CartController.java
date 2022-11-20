package com.zerobase.fitme.controller;

import static com.zerobase.fitme.exception.type.CartErrorCode.INVALID_REQUEST;

import com.zerobase.fitme.dto.ItemDto;
import com.zerobase.fitme.exception.CartException;
import com.zerobase.fitme.dto.CartDto;
import com.zerobase.fitme.service.CartService;
import com.zerobase.fitme.service.ItemService;
import java.security.Principal;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER')")// 모두 접속가능
    @PostMapping("/{itemId}")
    public ResponseEntity<String> register(Principal principal, @PathVariable Long itemId,
        @RequestBody @Valid CartDto.Request request, BindingResult bindingResult){
        // @valid 발생
        validation(bindingResult);

        cartService.register(principal.getName(), itemId, request);

        return ResponseEntity.ok("장바구니 등록이 완료되었습니다.");
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER')")// 모두 접속가능
    @GetMapping
    public ResponseEntity<List<CartDto.Response>> readCartList(Principal principal){
        return ResponseEntity.ok(cartService.readCategoryList(principal.getName()));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER')")// 모두 접속가능
    @PatchMapping("/{cartId}")
    public ResponseEntity<CartDto.Response> patchCart(@PathVariable Long cartId,
                                                        @RequestBody CartDto.RequestPatch request){
        return ResponseEntity.ok(cartService.patch(cartId, request));
    }

    private static void validation(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            throw new CartException(INVALID_REQUEST, list.get(0).getDefaultMessage());
        }
    }
}

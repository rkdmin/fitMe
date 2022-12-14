package com.zerobase.fitme.controller;

import static com.zerobase.fitme.exception.type.OrderErrorCode.INVALID_REQUEST;

import com.zerobase.fitme.dto.OrderDto;
import com.zerobase.fitme.dto.OrderDto.ResponseManager;
import com.zerobase.fitme.exception.OrderException;
import com.zerobase.fitme.service.OrderService;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER')")// 모두 접속가능
    @PostMapping("/{itemId}")
    public ResponseEntity<String> register(Principal principal, @PathVariable Long itemId){

        orderService.register(principal.getName(), itemId);

        return ResponseEntity.ok("결제가 완료되었습니다.");
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER')")// 모두 접속가능
    @GetMapping
    public ResponseEntity<List<OrderDto.Response>> readOrderList(Principal principal){
        return ResponseEntity.ok(orderService.readOrderList(principal.getName()));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER')")// 모두 접속가능
    @DeleteMapping("/{orderId}")
    public ResponseEntity<OrderDto.Response> delete(@PathVariable Long orderId){
        return ResponseEntity.ok(orderService.delete(orderId));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")// 매니저와 관리자만 접속가능
    @GetMapping("/status/ready/manager-admin")
    public ResponseEntity<Page<ResponseManager>> readStatusReady(final Pageable pageable){
        return ResponseEntity.ok(orderService.readStatusReady(pageable));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")// 매니저와 관리자만 접속가능
    @PatchMapping("/{orderId}/admin-manager")
    public ResponseEntity<ResponseManager> patchOrderStatus(@PathVariable Long orderId,
        @RequestBody @Valid OrderDto.RequestPatch request, BindingResult bindingResult){

        // @valid 발생
        validation(bindingResult);

        return ResponseEntity.ok(orderService.patchOrderStatus(request, orderId));
    }


    private static void validation(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            throw new OrderException(INVALID_REQUEST, list.get(0).getDefaultMessage());
        }
    }
}

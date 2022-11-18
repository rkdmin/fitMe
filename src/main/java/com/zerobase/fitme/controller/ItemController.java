package com.zerobase.fitme.controller;

import static com.zerobase.fitme.exception.type.ItemErrorCode.INVALID_REQUEST;

import com.zerobase.fitme.exception.ItemException;
import com.zerobase.fitme.model.RegItem;
import com.zerobase.fitme.service.ItemService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin-manager/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")// 관리자, 매니저만 접속가능
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegItem.Request request, BindingResult bindingResult){
        // @valid 발생
        validation(bindingResult);

        itemService.register(request);

        return ResponseEntity.ok("상품 등록이 완료되었습니다.");
    }

//    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")// 관리자, 매니저만 접속가능
//    @GetMapping("")
//    public List<Model> read(){
//        return modelService.read();
//    }
//
//    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")// 관리자, 매니저만 접속가능
//    @PatchMapping("/edit/{id}")
//    public Model patch(@RequestBody UdtModel.Request request, @PathVariable Long id){
//
//        return modelService.patch(request, id);
//    }
//
//    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")// 관리자, 매니저만 접속가능
//    @DeleteMapping("/delete/{id}")
//    public String delete(@PathVariable Long id){
//        modelService.delete(id);
//        return "모델 삭제가 완료되었습니다.";
//    }

    private static void validation(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            throw new ItemException(INVALID_REQUEST, list.get(0).getDefaultMessage());
        }
    }
}

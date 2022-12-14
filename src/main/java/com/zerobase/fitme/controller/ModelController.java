package com.zerobase.fitme.controller;

import static com.zerobase.fitme.exception.type.ModelErrorCode.INVALID_REQUEST;

import com.zerobase.fitme.entity.Model;
import com.zerobase.fitme.exception.ModelException;
import com.zerobase.fitme.dto.ModelDto;
import com.zerobase.fitme.model.UdtModel;
import com.zerobase.fitme.service.ModelService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/admin-manager/model")
@RequiredArgsConstructor
public class ModelController {
    private final ModelService modelService;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")// 관리자, 매니저만 접속가능
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid ModelDto.Request request, BindingResult bindingResult){
        // @valid 발생
        validation(bindingResult);

        modelService.register(request);

        return ResponseEntity.ok("모델 등록이 완료되었습니다.");
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")// 관리자, 매니저만 접속가능
    @GetMapping("")
    public ResponseEntity<List<Model>> read(){
        return ResponseEntity.ok(modelService.read());
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")// 관리자, 매니저만 접속가능
    @PatchMapping("/edit/{id}")
    public ResponseEntity<Model> patch(@RequestBody UdtModel.Request request, @PathVariable Long id){
        return ResponseEntity.ok(modelService.patch(request, id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")// 관리자, 매니저만 접속가능
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        modelService.delete(id);
        return ResponseEntity.ok("모델 삭제가 완료되었습니다.");
    }

    private static void validation(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            throw new ModelException(INVALID_REQUEST, list.get(0).getDefaultMessage());
        }
    }
}

package com.zerobase.fitme.controller;

import static com.zerobase.fitme.type.ErrorCode.*;

import com.zerobase.fitme.entity.Member;
import com.zerobase.fitme.exception.MemberException;
import com.zerobase.fitme.model.Auth;
import com.zerobase.fitme.security.TokenProvider;
import com.zerobase.fitme.service.MemberService;
import com.zerobase.fitme.type.ErrorCode;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    public String signup(@RequestBody @Valid Auth.SignUp request, BindingResult bindingResult){
        // @valid 발생
        validation(bindingResult);

        memberService.register(request);

        return "회원가입이 완료되었습니다.";
    }

    @PostMapping("/signin")
    public String signin(@RequestBody @Valid Auth.SignIn request, BindingResult bindingResult){
        // @valid 발생
        validation(bindingResult);

        Member member = memberService.login(request);

        String token = tokenProvider.generateToken(member.getUsername(), member.getRoles());

        return token;
    }

    @PreAuthorize("hasRole('ADMIN')") // 이런식으로 권한 주기 가능
    @GetMapping("/test")
    public String test(){
        return "권한 테스트";
    }

    private static void validation(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            throw new MemberException(INTERNAL_SERVER_ERROR, list.get(0).getDefaultMessage().toString());
        }
    }
}

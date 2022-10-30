package com.zerobase.fitme.service;

import com.zerobase.fitme.entity.Member;
import com.zerobase.fitme.exception.MemberException;
import com.zerobase.fitme.mail.MailComponents;
import com.zerobase.fitme.model.Auth;
import com.zerobase.fitme.model.Auth.SignUp;
import com.zerobase.fitme.repository.MemberRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.zerobase.fitme.type.Authority.*;
import static com.zerobase.fitme.type.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final MailComponents mailComponents;

    @Value("${spring.path}")
    private String path;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.memberRepository.findByUsername(username)
            .orElseThrow(() -> new MemberException(USER_NOT_FOUND));
    }

    public Member register(Auth.SignUp request){
        // 유효성 검사
        validationRegister(request);
        Optional<Member> optionalMember = memberRepository.findByUsername(request.getUsername());
        if(optionalMember.isPresent()){
            throw new MemberException(ALREADY_EXIST_ID);
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        Member member = memberRepository.save(Member.toEntity(request));

        // 회원저장되면 이메일 전송
        sendEmail(member.getEmail(), member.getEmailKey());

        return member;
    }

    private void validationRegister(SignUp member) {
        String roles = member.getRoles();
        if(!roles.equals(ROLE_USER.toString()) && !roles.equals(ROLE_ADMIN.toString())
            && !roles.equals(ROLE_MANAGER.toString())){
            throw new MemberException(WRONG_ROLES);
        }
    }

    public Member login(Auth.SignIn member){
        Optional<Member> optionalMember = memberRepository.findByUsername(member.getUsername());
        if(!optionalMember.isPresent()){
            throw new MemberException(LOGIN_FAIL);
        }

        if(!passwordEncoder.matches(member.getPassword(), optionalMember.get().getPassword())){
            throw new MemberException(LOGIN_FAIL);
        }

        return optionalMember.get();
    }

    private void sendEmail(String email, String emailKey) {
        String subject = "fitMe 가입을 축하드립니다. ";
        String text = "<p>fitMe 가입을 축하드립니다.</p>" +
            "<p>아래 링크를 클릭하셔서 가입을 완료 하세요.</p>" +
            "<div><a target='_blank' href='http://"+ path +"/email?id=" + emailKey + "'> 가입 완료 </a></div>";
        mailComponents.sendMail(email, subject, text);
    }
}

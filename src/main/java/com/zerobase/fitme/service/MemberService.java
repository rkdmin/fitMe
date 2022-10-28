package com.zerobase.fitme.service;

import com.zerobase.fitme.entity.Member;
import com.zerobase.fitme.exception.MemberException;
import com.zerobase.fitme.model.Auth;
import com.zerobase.fitme.model.Auth.SignUp;
import com.zerobase.fitme.repository.MemberRepository;
import com.zerobase.fitme.type.Authority;
import com.zerobase.fitme.type.ErrorCode;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.zerobase.fitme.type.Authority.*;
import static com.zerobase.fitme.type.ErrorCode.*;

@Slf4j
@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.memberRepository.findByUsername(username)
            .orElseThrow(() -> new MemberException(USER_NOT_FOUND));
    }

    public Member register(Auth.SignUp member){
        // 유효성 검사
        validationRegister(member);
        Optional<Member> optionalMember = memberRepository.findByUsername(member.getUsername());
        if(optionalMember.isPresent()){
            throw new MemberException(ALREADY_EXIST_ID);
        }

        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return memberRepository.save(member.toEntity());
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


}

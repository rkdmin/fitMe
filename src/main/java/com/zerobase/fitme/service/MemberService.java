package com.zerobase.fitme.service;

import com.zerobase.fitme.entity.Member;
import com.zerobase.fitme.exception.MemberException;
import com.zerobase.fitme.model.Auth;
import com.zerobase.fitme.repository.MemberRepository;
import com.zerobase.fitme.type.ErrorCode;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.memberRepository.findByUsername(username)
            .orElseThrow(() -> new MemberException(ErrorCode.USER_NOT_FOUND));
    }

    public Member register(Auth.SignUp member){
        Optional<Member> optionalMember = this.memberRepository.findByUsername(member.getUsername());
        if(!optionalMember.isPresent()){
            throw new MemberException(ErrorCode.ALREADY_EXIST_ID);
        }

        member.setPassword(this.passwordEncoder.encode(member.getPassword()));
        return memberRepository.save(member.toEntity());
    }

    public Member authenticate(){
        return null;
    }

}

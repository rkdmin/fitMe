package com.zerobase.fitme.member;

import static com.zerobase.fitme.exception.type.MemberErrorCode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.zerobase.fitme.dto.BrandDto;
import com.zerobase.fitme.dto.MemberDto.SignIn;
import com.zerobase.fitme.dto.MemberDto.SignUp;
import com.zerobase.fitme.entity.Member;
import com.zerobase.fitme.exception.BrandException;
import com.zerobase.fitme.exception.MemberException;
import com.zerobase.fitme.exception.type.BrandErrorCode;
import com.zerobase.fitme.exception.type.MemberErrorCode;
import com.zerobase.fitme.mail.MailComponents;
import com.zerobase.fitme.repository.MemberDetailRepository;
import com.zerobase.fitme.repository.MemberRepository;
import com.zerobase.fitme.service.MemberService;
import com.zerobase.fitme.type.EmailStatus;
import java.util.AbstractList;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private MailComponents mailComponents;
    @Mock
    private MemberDetailRepository memberDetailRepository;
    @InjectMocks
    private MemberService memberService;

    @Test
    void 회원가입_실패_잘못된권한() {
        // given
        String roles = "ROLE_MEMBER";// 잘못된 권한
        SignUp request = SignUp.builder().roles(roles).build();

        // when
        MemberException exception = assertThrows(MemberException.class,
            () -> memberService.register(request));

        // then
        assertEquals(WRONG_ROLES, exception.getErrorCode());
    }

    @Test
    void 회원가입_실패_중복된아이디() {
        // given
        String username = "user@naver.com";// 중복 아이디
        String roles = "ROLE_USER";
        SignUp request = SignUp.builder().username(username).roles(roles).build();

        given(memberRepository.findByUsername(anyString()))
            .willReturn(Optional.of(Member.builder().username(username).build()));

        // when
        MemberException exception = assertThrows(MemberException.class,
            () -> memberService.register(request));

        // then
        assertEquals(ALREADY_EXIST_ID, exception.getErrorCode());
    }

    @Test
    void 회원가입_성공() {
        // given
        String username = "user123";
        String roles = "ROLE_USER";
        String email = "22rkdmin@naver.com";
        String password = "abc1234!";
        String encodePassword = "encode" + password;

        SignUp request = SignUp.builder()
            .username(username)
            .email(email)
            .password(password)
            .roles(roles)
            .build();
        Member member = Member.toEntity(request);

        given(memberRepository.findByUsername(anyString()))
            .willReturn(Optional.empty());
        given(passwordEncoder.encode(password))
            .willReturn(encodePassword);
        given(memberRepository.save(any()))
            .willReturn(member);

        ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);

        // when
        memberService.register(request);

        // then
        verify(memberRepository, times(1)).save(captor.capture());
        assertEquals(encodePassword, captor.getValue().getPassword());
    }

    @Test
    void 로그인_실패_잘못된아이디() {
        // given
        String username = "user123";
        SignIn request = SignIn.builder().username(username).build();

        given(memberRepository.findByUsername(anyString()))
            .willReturn(Optional.empty());

        // when
        MemberException exception = assertThrows(MemberException.class,
            () -> memberService.login(request));

        // then
        assertEquals(LOGIN_FAIL, exception.getErrorCode());
    }

    @Test
    void 로그인_실패_잘못된패스워드() {
        // given
        String username = "user123";
        String requestPassword = "abc123!!";// 새로 요청하는 비밀번호(4가빠짐)
        String password = "encodeabc1234!!";// 원래 비밀번호
        SignIn request = SignIn.builder()
            .username(username)
            .password(requestPassword)
            .build();

        given(memberRepository.findByUsername(anyString()))
            .willReturn(Optional.of(Member.builder().password(password).build()));
        given(passwordEncoder.encode(any()))
            .willReturn("encode" + requestPassword);

        // when
        MemberException exception = assertThrows(MemberException.class,
            () -> memberService.login(request));

        // then
        assertEquals(LOGIN_FAIL, exception.getErrorCode());
    }

    @Test
    void 로그인_실패_인증미완료이메일() {
        // given
        String username = "user123";
        String requestPassword = "abc1234!!";
        String password = "encodeabc1234!!";
        SignIn request = SignIn.builder()
            .username(username)
            .password(requestPassword)
            .build();

        given(memberRepository.findByUsername(anyString()))
            .willReturn(Optional.of(Member.builder()
                    .password(password)
                    .emailStatus(EmailStatus.F)// 인증 미완료
                    .build()));
        given(passwordEncoder.encode(any()))
            .willReturn("encode" + requestPassword);

        // when
        MemberException exception = assertThrows(MemberException.class,
            () -> memberService.login(request));

        // then
        assertEquals(LOGIN_FAIL, exception.getErrorCode());
    }

    @Test
    void 로그인_성공() {
        // given
        String username = "user123";
        String requestPassword = "abc1234!!";
        String password = "encodeabc1234!!";
        SignIn request = SignIn.builder()
            .username(username)
            .password(requestPassword)
            .build();

        given(memberRepository.findByUsername(anyString()))
            .willReturn(Optional.of(Member.builder()
                .password(password)
                .emailStatus(EmailStatus.S)// 인증 성공
                .build()));
        given(passwordEncoder.encode(any()))
            .willReturn("encode" + requestPassword);

        // when
        memberService.login(request);

        // then
    }
}
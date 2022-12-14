package com.zerobase.fitme.service;

import static com.zerobase.fitme.exception.type.MemberErrorCode.ALREADY_EXIST_ID;
import static com.zerobase.fitme.exception.type.MemberErrorCode.INVALID_EMAIL_KEY;
import static com.zerobase.fitme.exception.type.MemberErrorCode.LOGIN_FAIL;
import static com.zerobase.fitme.exception.type.MemberErrorCode.USER_NOT_FOUND;
import static com.zerobase.fitme.exception.type.MemberErrorCode.WRONG_ROLES;
import static com.zerobase.fitme.type.Authority.ROLE_ADMIN;
import static com.zerobase.fitme.type.Authority.ROLE_MANAGER;
import static com.zerobase.fitme.type.Authority.ROLE_USER;

import com.zerobase.fitme.entity.Member;
import com.zerobase.fitme.entity.MemberDetail;
import com.zerobase.fitme.exception.MemberException;
import com.zerobase.fitme.mail.MailComponents;
import com.zerobase.fitme.dto.MemberDto;
import com.zerobase.fitme.dto.MemberDto.SignUp;
import com.zerobase.fitme.repository.MemberDetailRepository;
import com.zerobase.fitme.repository.MemberRepository;
import com.zerobase.fitme.type.EmailStatus;
import com.zerobase.fitme.type.MemberStatus;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final MailComponents mailComponents;
    private final MemberDetailRepository memberDetailRepository;

    @Value("${spring.path}")
    private String path;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.memberRepository.findByUsername(username)
            .orElseThrow(() -> new MemberException(USER_NOT_FOUND));
    }

    /**
     * ????????????
     * @param request
     * @return
     */
    public Member register(MemberDto.SignUp request){
        // ????????? ??????
        validationRegister(request);
        Optional<Member> optionalMember = memberRepository.findByUsername(request.getUsername());
        if(optionalMember.isPresent()){
            throw new MemberException(ALREADY_EXIST_ID);
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        Member member = memberRepository.save(Member.toEntity(request));

        // ?????????????????? ????????? ??????
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

    /**
     * ?????????
     * @param member
     * @return
     */
    public Member login(MemberDto.SignIn member){
        Optional<Member> optionalMember = memberRepository.findByUsername(member.getUsername());
        if(!optionalMember.isPresent()){
            throw new MemberException(LOGIN_FAIL);
        }

        if(!passwordEncoder.matches(member.getPassword(), optionalMember.get().getPassword())){
            throw new MemberException(LOGIN_FAIL);
        }

        // ????????? ??? ??? ???????????? ????????????
//        if(optionalMember.get().getEmailStatus() == EmailStatus.F){
//            throw new MemberException(LOGIN_FAIL);
//        }

        return optionalMember.get();
    }

    /**
     * ????????????
     * @param email
     * @param emailKey
     */
    private void sendEmail(String email, String emailKey) {
        String subject = "fitMe ????????? ??????????????????. ";
        String text = "<p>fitMe ????????? ??????????????????.</p>" +
            "<p>?????? ????????? ??????????????? ????????? ?????? ?????????.</p>" +
            "<div><a target='_blank' href='http://"+ path +"/member/email/" + emailKey + "'> ?????? ?????? </a></div>";
        mailComponents.sendMail(email, subject, text);
    }

    /**
     * ?????? ??????
     * @param emailKey
     */
    public void emailAuth(String emailKey) {
        Optional<Member> optionalMember = memberRepository.findByEmailKey(emailKey);

        if(!optionalMember.isPresent()){
            throw new MemberException(INVALID_EMAIL_KEY);
        }

        // ?????? ????????? ??????
        MemberDetail memberDetail = MemberDetail.builder().status(MemberStatus.S).build();

        // ?????? ??????
        Member member = optionalMember.get();
        member.setEmailKey(null);// ????????? ?????? ?????????
        member.setEmailStatus(EmailStatus.S);

        // ????????????
        memberRepository.save(member);
        member.setMemberDetail(memberDetailRepository.save(memberDetail));
    }

    /**
     * username?????? Member??????
     * @param username
     * @return
     */
    public Member findByUserName(String username) {
        return memberRepository.findByUsername(username).orElseThrow(
            () -> new MemberException(USER_NOT_FOUND)
        );
    }
}

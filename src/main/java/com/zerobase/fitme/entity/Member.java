package com.zerobase.fitme.entity;

import static com.zerobase.fitme.type.EmailStatus.*;

import com.zerobase.fitme.model.Auth.SignUp;
import com.zerobase.fitme.type.EmailStatus;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String emailKey;

    @Enumerated(EnumType.STRING)
    private EmailStatus emailStatus;

    private String password;

    // 권한 정보
    private String roles;

    // 멤버상세정보, 멤버사이즈정보 일대일 매핑
    @OneToOne
    @JoinColumn(name = "member_detail_id")
    private MemberDetail memberDetail;
    @OneToOne
    @JoinColumn(name = "member_size_id")
    private MemberSize memberSize;

    public static Member toEntity(SignUp request) {
        return Member.builder()
            .username(request.getUsername())
            .email(request.getEmail())
            .emailKey(UUID.randomUUID().toString())// 랜덤 uuid키
            .emailStatus(F)// 인증 미완료
            .password(request.getPassword())// 서비스에서 암호화 처리함
            .roles(request.getRoles())
            .build();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        roles.add(new SimpleGrantedAuthority(getRoles()));

        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

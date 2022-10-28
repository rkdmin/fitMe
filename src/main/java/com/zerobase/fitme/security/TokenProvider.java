package com.zerobase.fitme.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class TokenProvider {
    @Value("{spring.jwt.secret")
    private String secretKey;
    private static final String KEY_ROLES = "roles";
    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60;// 1시간

    /**
     * 토큰 발급
     * @param username
     * @param roles
     * @return
     */
    public String generateToken(String username, String roles){

        Claims claims = Jwts.claims().setSubject(username);
        claims.put(KEY_ROLES, roles);

        var now = new Date();
        var expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)// 토큰 생성 시간
                .setExpiration(expiredDate)// 토큰 만료 시간
                .signWith(SignatureAlgorithm.HS512, this.secretKey)// 사용할 암호화 알고리즘, 비밀키
                .compact();
    }

    public String getUsername(String token){
        return this.parseClaims(token).getSubject();// username 리턴
    }

    public boolean validateToken(String token){
        if(!StringUtils.hasText(token)) return false;

        var claims = parseClaims(token);

        return !claims.getExpiration().before(new Date());// 시간 만료면 false 리턴
    }

    private Claims parseClaims(String token){
        try{
            return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
        }catch(ExpiredJwtException e){
            return e.getClaims();
        }
    }
}

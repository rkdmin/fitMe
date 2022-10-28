package com.zerobase.fitme.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";// Jwt토큰을 사용하면 앞에 보통 붙인다.
    public final TokenProvider tokenProvider;

    // 사용자가 api를 호출하면 controller로 가기 전에 이 필터를 거치게 된다.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String token = resolveTokenFromRequest(request);

        if(StringUtils.hasText(token)){
            // 토큰 유효성 검증
            Authentication auth = tokenProvider.getAuthentication(token);
            // 유효하다면 Context에 담기
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveTokenFromRequest(HttpServletRequest request){
        String token = request.getHeader(TOKEN_HEADER);// 키에 해당하는 헤더의 벨류가 나온다.

        // 토큰이 있으면
        if(!ObjectUtils.isEmpty(token) && token.startsWith(TOKEN_PREFIX)){
            return token.substring(TOKEN_PREFIX.length());// Bearer 이후의 토큰을 리턴
        }

        // 없으면
        return null;
    }

}

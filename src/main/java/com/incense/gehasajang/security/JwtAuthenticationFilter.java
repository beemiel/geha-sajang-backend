package com.incense.gehasajang.security;

import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.util.JwtProperties;
import com.incense.gehasajang.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private JwtUtil jwtUtil;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
    }

    /**
     * 요청을 처리하기 전 이 필터가 실행됨
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Authentication authentication = getAuthentication(request);

        if(authentication != null) {
            //Controller에서 Authentication 객체를 쓰기 위해 보안 컨텍스트에 인증 객체를 넣어줌
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication);
        }

        chain.doFilter(request,response);
    }

    /**
     * jwt 토큰을 확인하고 인증 객체를 만들어 반환한다.
     */
    private Authentication getAuthentication(HttpServletRequest request) {
        String authorization = request.getHeader(JwtProperties.HEADER_STRING);
        if(authorization == null) {
            return null;
        }

        String token = authorization.substring(JwtProperties.TOKEN_PREFIX.length());

        Claims claims = null;

        try {
            claims = jwtUtil.parseToken(token);
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", ErrorCode.EXPIRED_TOKEN.getCode());
        } catch (JwtException e) {
            request.setAttribute("exception", ErrorCode.INVALID_TOKEN.getCode());
        }

        return new UserAuthentication(claims);
    }
}

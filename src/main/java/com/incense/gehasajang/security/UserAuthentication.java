package com.incense.gehasajang.security;

import com.incense.gehasajang.domain.host.HostRole;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

//커스텀 인증 객체
public class UserAuthentication extends AbstractAuthenticationToken {

    private Claims claims;

    public UserAuthentication(Claims claims) {
        super(authorities(claims));
        this.claims = claims;
    }

    private static List<GrantedAuthority> authorities(Claims claims) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(HostRole.ROLE_SUB.getRole()));

        //메인 호스트의 경우 역할 추가
        String role = claims.get("role", String.class);
        if (role.equals(HostRole.ROLE_MAIN.getType())) {
            authorities.add(new SimpleGrantedAuthority(HostRole.ROLE_MAIN.getRole()));
        }

        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    /**
     * 인증된 사용자 정보.
     * 이게 null이면 컨트롤러로 전달되지 않음
     */
    @Override
    public Object getPrincipal() {
        return claims;
    }

    @Override
    public boolean isAuthenticated() {
        return claims != null;
    }

    public String getAccount() {
        return claims.get("account", String.class);
    }

    public String getRole() {
        return claims.get("role", String.class);
    }
}

package com.example.jpabook.auth;

import com.example.jpabook.domain.Member;
import com.example.jpabook.domain.MemberRole;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
public class MyMemberDetail implements UserDetails {
    private static final String ROLE_PREFIX = "ROLE_";
    private final Member member;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        MemberRole role = member.getMemberRole();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(ROLE_PREFIX + role.toString());
        Collection<GrantedAuthority> authorities = new ArrayList<>(); //List인 이유 : 여러개의 권한을 가질 수 있다
        authorities.add(authority);
        return authorities;

    }

    @Override
    public String getPassword() {
        return this.member.getPw();
    }

    @Override
    public String getUsername() {
        return this.member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

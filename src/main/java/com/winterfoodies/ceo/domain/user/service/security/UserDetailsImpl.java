package com.winterfoodies.ceo.domain.user.service.security;

import com.winterfoodies.ceo.dto.user.UserRequestDto;
import com.winterfoodies.ceo.dto.user.UserResponseDto;
import com.winterfoodies.ceo.entities.User;
import com.winterfoodies.ceo.entities.enums.user.UserType;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {


    private static final String ROLE_PREFIX = "ROLE_";
    private final User user;
    private final String redirect;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserType userRole = user.getRole();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(ROLE_PREFIX + userRole.toString());
        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    public UserResponseDto getUserResponseDto(){
        return UserResponseDto.of(user, HttpStatus.OK, redirect);
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

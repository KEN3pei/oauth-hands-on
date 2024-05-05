package com.auth.client_application.services;

import java.util.Collection;
import com.auth.client_application.jooq.tables.records.UsersRecord;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

public class CustomUserDetails implements UserDetails {
    private UsersRecord jUser;

    public CustomUserDetails(UsersRecord jUser) {
        this.jUser = jUser;
    }

    public String getUserId()
    {
        return this.jUser.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.jUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.jUser.getEmail();
    }

    // 以下でfalseがひとつでも返ってきた場合は401Unauthorizedのメッセージが返される

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

package com.theeduconnect.exeeduconnectbe.features.authentication.dtos;

import java.util.Collection;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;

public class OAuth2User implements org.springframework.security.oauth2.core.user.OAuth2User {

    private org.springframework.security.oauth2.core.user.OAuth2User oauth2User;

    public OAuth2User(org.springframework.security.oauth2.core.user.OAuth2User oauth2User) {
        this.oauth2User = oauth2User;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oauth2User.getAttribute("name");
    }

    public String getEmail() {
        return oauth2User.<String>getAttribute("email");
    }

    public String getAvatarUrl() {
        return oauth2User.getAttribute("picture");
    }
}

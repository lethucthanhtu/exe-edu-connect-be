package com.theeduconnect.exeeduconnectbe.features.authentication.services.impl;

import com.theeduconnect.exeeduconnectbe.features.authentication.dtos.OAuth2User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class LoadOAuth2UserServiceImpl extends DefaultOAuth2UserService {
    @Override
    public org.springframework.security.oauth2.core.user.OAuth2User loadUser(
            OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        org.springframework.security.oauth2.core.user.OAuth2User user = super.loadUser(userRequest);
        return new OAuth2User(user);
    }
}

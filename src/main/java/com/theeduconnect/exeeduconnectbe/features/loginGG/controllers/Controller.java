package com.theeduconnect.exeeduconnectbe.features.loginGG.controllers;

import com.theeduconnect.exeeduconnectbe.features.loginGG.dtos.Root;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/SSO")
public class Controller {
    @GetMapping("/signingoogle")
    public Map<String, Object> currentUser(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        System.out.println(toPerson(oAuth2AuthenticationToken.getPrincipal().getAttributes()).getEmail());
        System.out.println(toPerson(oAuth2AuthenticationToken.getPrincipal().getAttributes()).getName());
        System.out.println(toPerson(oAuth2AuthenticationToken.getPrincipal().getAttributes()).getPicture());
        return oAuth2AuthenticationToken.getPrincipal().getAttributes();
    }

    public Root toPerson(Map<String, Object> map) {
        if (map==null) {
            return null;
        }
        Root root = new Root();
        root.setEmail(map.get("email").toString());
        root.setName(map.get("name").toString());
        root.setPicture(map.get("picture").toString());
        return root;
    }
}

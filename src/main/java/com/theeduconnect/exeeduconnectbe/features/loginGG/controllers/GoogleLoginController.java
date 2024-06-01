package com.theeduconnect.exeeduconnectbe.features.loginGG.controllers;

import com.theeduconnect.exeeduconnectbe.constants.authentication.AuthenticationEndpoints;
import jakarta.servlet.http.HttpSession;
import java.net.URI;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoogleLoginController {
    @GetMapping(AuthenticationEndpoints.GOOGLE_LOGIN_URL)
    public ResponseEntity<Void> getRoleId(HttpSession session, @PathVariable int roleId) {
        session.setAttribute("roleId", roleId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(AuthenticationEndpoints.GOOGLE_LOGIN_REDIRECT_URL));
        return new ResponseEntity<>(
                headers, HttpStatusCode.valueOf(HttpStatus.SC_MOVED_PERMANENTLY));
    }
}

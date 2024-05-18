/* (C) 2024 */
package com.theeduconnect.exeeduconnectbe.features.authentication.controllers;

import com.theeduconnect.exeeduconnectbe.constants.authentication.endpoints.AuthenticationEndpoints;
import com.theeduconnect.exeeduconnectbe.features.authentication.payload.request.LoginRequest;
import com.theeduconnect.exeeduconnectbe.features.authentication.payload.request.RegisterRequest;
import com.theeduconnect.exeeduconnectbe.features.authentication.payload.response.AuthenticationServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.authentication.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(AuthenticationEndpoints.LOGIN_URL)
    @Operation(summary = "Logs into an existing account.")
    public ResponseEntity<AuthenticationServiceResponse> Login(
            @Valid @RequestBody LoginRequest request) {
        AuthenticationServiceResponse response = authenticationService.Login(request);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PostMapping(AuthenticationEndpoints.REGISTER_URL)
    @Operation(summary = "Creates a new User account.")
    public ResponseEntity<AuthenticationServiceResponse> Register(
            @Valid @RequestBody RegisterRequest request) {
        AuthenticationServiceResponse response = authenticationService.Register(request);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping(AuthenticationEndpoints.ROLES_URL)
    @Operation(summary = "Gets a list of roles in the system.")
    public ResponseEntity<AuthenticationServiceResponse> GetRoles() {
        AuthenticationServiceResponse response = authenticationService.GetRoles();
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping(AuthenticationEndpoints.LOGOUT_URL)
    @Operation(
            summary =
                    "Logs the current user out of the system. Also clears the security credential"
                            + " session.")
    public ResponseEntity<String> Logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        String redirectUrl = "/login";
        return ResponseEntity.status(HttpStatus.FOUND).body(redirectUrl);
    }
}

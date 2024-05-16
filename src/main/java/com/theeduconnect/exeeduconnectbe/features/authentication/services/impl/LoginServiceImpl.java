package com.theeduconnect.exeeduconnectbe.features.authentication.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.AuthenticationMapper;
import com.theeduconnect.exeeduconnectbe.constants.authentication.responseCodes.AuthenticationHttpResponseCodes;
import com.theeduconnect.exeeduconnectbe.constants.authentication.serviceMessages.AuthenticationServiceMessages;
import com.theeduconnect.exeeduconnectbe.domain.entities.User;
import com.theeduconnect.exeeduconnectbe.features.authentication.payload.request.LoginRequest;
import com.theeduconnect.exeeduconnectbe.features.authentication.payload.response.AuthenticationServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.authentication.services.JwtService;
import com.theeduconnect.exeeduconnectbe.repositories.RoleRepository;
import com.theeduconnect.exeeduconnectbe.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

public class LoginServiceImpl {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationMapper authenticationMapper;
    private final AuthenticationManager authenticationManager;
    private LoginRequest request;
    private String jwtToken;
    private User user;

    public LoginServiceImpl(
            RoleRepository roleRepository,
            AuthenticationMapper authenticationMapper,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {
        this.roleRepository = roleRepository;
        this.authenticationMapper = authenticationMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthenticationServiceResponse Handle(LoginRequest request) {
        try {
            this.request = request;
            AuthenticateUser();
            jwtToken = jwtService.generateToken(user);
            return SuccessfulLoginResult();
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                return InvalidLoginCredentialsResult();
            }
            return InternalServerErrorResult(e);
        }
    }

    private void AuthenticateUser() {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        user = userRepository.findUserByEmail(request.getEmail()).get();
    }

    private AuthenticationServiceResponse SuccessfulLoginResult() {
        return new AuthenticationServiceResponse(
                AuthenticationHttpResponseCodes.SUCCESSFUL_LOGIN,
                AuthenticationServiceMessages.SUCCESSFUL_LOGIN,
                jwtToken);
    }

    private AuthenticationServiceResponse InvalidRoleResult() {
        return new AuthenticationServiceResponse(
                AuthenticationHttpResponseCodes.INVALID_ROLE_RESULT,
                AuthenticationServiceMessages.INVALID_ROLE_RESULT,
                null);
    }

    private AuthenticationServiceResponse InvalidLoginCredentialsResult() {
        return new AuthenticationServiceResponse(
                AuthenticationHttpResponseCodes.INVALID_LOGIN_CREDENTIALS,
                AuthenticationServiceMessages.INVALID_LOGIN_CREDENTIALS,
                null);
    }

    private AuthenticationServiceResponse InternalServerErrorResult(Exception e) {
        return new AuthenticationServiceResponse(
                AuthenticationHttpResponseCodes.INTERNAL_SERVER_ERROR,
                AuthenticationServiceMessages.INTERNAL_SERVER_ERROR,
                e.getMessage());
    }
}

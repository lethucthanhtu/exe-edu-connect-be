package com.theeduconnect.exeeduconnectbe.features.authentication.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.AuthenticationMapper;
import com.theeduconnect.exeeduconnectbe.constants.authentication.messages.AuthenticationServiceMessages;
import com.theeduconnect.exeeduconnectbe.constants.authentication.responseCodes.AuthenticationHttpResponseCodes;
import com.theeduconnect.exeeduconnectbe.domain.entities.Role;
import com.theeduconnect.exeeduconnectbe.domain.entities.User;
import com.theeduconnect.exeeduconnectbe.features.authentication.dtos.RoleDto;
import com.theeduconnect.exeeduconnectbe.features.authentication.payload.request.LoginRequest;
import com.theeduconnect.exeeduconnectbe.features.authentication.payload.response.AuthenticationServiceResponse;
import com.theeduconnect.exeeduconnectbe.repositories.RoleRepository;
import com.theeduconnect.exeeduconnectbe.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

public class LoginServiceImpl {
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private AuthenticationMapper authenticationMapper;
    private List<Role> roles;
    private LoginRequest request;
    private List<RoleDto> roleDtos;

    public LoginServiceImpl(RoleRepository roleRepository,
                            AuthenticationMapper authenticationMapper,
                            UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.authenticationMapper = authenticationMapper;
        this.userRepository = userRepository;
    }

    public AuthenticationServiceResponse Handle(LoginRequest request) {
        try {
            this.request = request;
            if (!IsRoleValid()) return InvalidRoleResult();
            if (!AreLoginCredentialsValid()) return InvalidLoginCredentialsResult();
            return new AuthenticationServiceResponse(0, null, null);
        } catch (Exception e) {
            return InternalServerErrorResult(e);
        }
    }

    private boolean IsRoleValid() {
        Optional<Role> roleOptional = roleRepository.findById(request.getRole());
        if (roleOptional.isEmpty()) return false;
        return true;
    }

    private boolean AreLoginCredentialsValid() {
        User user = userRepository.findUserByEmail(request.getEmail());
        if (user == null) return false;

        return true;
    }

    private AuthenticationServiceResponse InvalidRoleResult() {
        return new AuthenticationServiceResponse(
                AuthenticationHttpResponseCodes.INVALID_ROLE_RESULT,
                AuthenticationServiceMessages.INVALID_ROLE_RESULT,
                null
        );
    }

    private AuthenticationServiceResponse InvalidLoginCredentialsResult() {
        return new AuthenticationServiceResponse(
                AuthenticationHttpResponseCodes.INVALID_LOGIN_CREDENTIALS,
                AuthenticationServiceMessages.INVALID_LOGIN_CREDENTIALS,
                null
        );
    }

    private AuthenticationServiceResponse InternalServerErrorResult(Exception e) {
        return new AuthenticationServiceResponse(
                AuthenticationHttpResponseCodes.INTERNAL_SERVER_ERROR,
                AuthenticationServiceMessages.INTERNAL_SERVER_ERROR,
                e.getMessage()
        );
    }
}

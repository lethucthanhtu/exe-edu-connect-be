package com.theeduconnect.exeeduconnectbe.features.authentication.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.AuthenticationMapper;
import com.theeduconnect.exeeduconnectbe.constants.authentication.messages.AuthenticationServiceMessages;
import com.theeduconnect.exeeduconnectbe.constants.authentication.responseCodes.AuthenticationHttpResponseCodes;
import com.theeduconnect.exeeduconnectbe.domain.entities.Role;
import com.theeduconnect.exeeduconnectbe.domain.entities.User;
import com.theeduconnect.exeeduconnectbe.features.authentication.payload.response.AuthenticationServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.authentication.payload.request.RegisterRequest;
import com.theeduconnect.exeeduconnectbe.repositories.RoleRepository;
import com.theeduconnect.exeeduconnectbe.repositories.UserRepository;

import java.util.Optional;

public class RegisterServiceImpl {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private AuthenticationMapper authenticationMapper;
    private RegisterRequest request;
    private User user;
    private Role role;


    public RegisterServiceImpl(UserRepository userRepository,
                               AuthenticationMapper authenticationMapper,
                               RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.authenticationMapper = authenticationMapper;
        this.roleRepository = roleRepository;
    }

    public AuthenticationServiceResponse Handle(RegisterRequest request) {
        try {
            this.request = request;
            if (IsEmailTaken()) return EmailIsTakenResult();
            if (!IsRoleValid()) return InvalidRoleResult();
            MapDtoToUserEntity();
            userRepository.save(user);
            return SuccessfulRegistrationResult();
        } catch (Exception e) {
            return InternalServerErrorResult(e);
        }

    }

    private boolean IsEmailTaken() {
        User user = userRepository.findUserByEmail(request.getEmail());
        if (user == null) return false;
        return true;
    }

    private boolean IsRoleValid() {
        Optional<Role> roleOptional = roleRepository.findById(request.getRole());
        if (roleOptional.isEmpty()) return false;
        role = roleOptional.get();
        return true;
    }

    private void MapDtoToUserEntity() {
        user = authenticationMapper.RegisterRequestToUserEntity(request);
        user.setRole(role);
    }

    private AuthenticationServiceResponse EmailIsTakenResult() {
        return new AuthenticationServiceResponse(
                AuthenticationHttpResponseCodes.EMAIL_IS_TAKEN,
                AuthenticationServiceMessages.EMAIL_IS_TAKEN,
                null
        );
    }

    private AuthenticationServiceResponse InvalidRoleResult() {
        return new AuthenticationServiceResponse(
                AuthenticationHttpResponseCodes.INVALID_ROLE_RESULT,
                AuthenticationServiceMessages.INVALID_ROLE_RESULT,
                null
        );
    }

    private AuthenticationServiceResponse SuccessfulRegistrationResult() {
        return new AuthenticationServiceResponse(
                AuthenticationHttpResponseCodes.SUCCESSFUL_REGISTRATION,
                AuthenticationServiceMessages.SUCCESSFUL_REGISTRATION,
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

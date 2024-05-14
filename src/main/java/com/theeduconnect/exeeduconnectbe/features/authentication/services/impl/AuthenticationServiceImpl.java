package com.theeduconnect.exeeduconnectbe.features.authentication.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.AuthenticationMapper;
import com.theeduconnect.exeeduconnectbe.features.authentication.payload.request.LoginRequest;
import com.theeduconnect.exeeduconnectbe.features.authentication.payload.response.AuthenticationServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.authentication.payload.request.RegisterRequest;
import com.theeduconnect.exeeduconnectbe.features.authentication.services.AuthenticationService;
import com.theeduconnect.exeeduconnectbe.repositories.RoleRepository;
import com.theeduconnect.exeeduconnectbe.repositories.UserRepository;


public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationMapper authenticationMapper;
    private RegisterServiceImpl registerServiceImpl;
    private GetRolesServiceImpl getRolesServiceImpl;
    private LoginServiceImpl loginServiceImpl;

    public AuthenticationServiceImpl(UserRepository userRepository,
                                     AuthenticationMapper authenticationMapper,
                                     RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.authenticationMapper = authenticationMapper;
        this.roleRepository = roleRepository;
        InitializeChildServices();
    }

    @Override
    public AuthenticationServiceResponse Register(RegisterRequest request) {
        return registerServiceImpl.Handle(request);
    }


    @Override
    public AuthenticationServiceResponse Login(LoginRequest request) {
        return loginServiceImpl.Handle(request);
    }

    @Override
    public AuthenticationServiceResponse GetRoles() {
        return getRolesServiceImpl.Handle();
    }

    private void InitializeChildServices() {
        registerServiceImpl = new RegisterServiceImpl(userRepository, authenticationMapper,roleRepository);
        getRolesServiceImpl = new GetRolesServiceImpl(roleRepository,authenticationMapper);

    }
}

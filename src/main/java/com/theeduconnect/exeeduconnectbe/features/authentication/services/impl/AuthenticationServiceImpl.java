package com.theeduconnect.exeeduconnectbe.features.authentication.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.AuthenticationMapper;
import com.theeduconnect.exeeduconnectbe.features.authentication.payload.request.LoginRequest;
import com.theeduconnect.exeeduconnectbe.features.authentication.payload.request.RegisterRequest;
import com.theeduconnect.exeeduconnectbe.features.authentication.payload.response.AuthenticationServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.authentication.services.AuthenticationService;
import com.theeduconnect.exeeduconnectbe.features.authentication.services.JwtService;
import com.theeduconnect.exeeduconnectbe.repositories.RoleRepository;
import com.theeduconnect.exeeduconnectbe.repositories.StudentRepository;
import com.theeduconnect.exeeduconnectbe.repositories.TeacherRepository;
import com.theeduconnect.exeeduconnectbe.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationMapper authenticationMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private RegisterServiceImpl registerServiceImpl;
    private GetRolesServiceImpl getRolesServiceImpl;
    private LoginServiceImpl loginServiceImpl;
    private final PasswordEncoder passwordEncoder;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public AuthenticationServiceImpl(
            UserRepository userRepository,
            AuthenticationMapper authenticationMapper,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            TeacherRepository teacherRepository,
            StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.authenticationMapper = authenticationMapper;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
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
        registerServiceImpl =
                new RegisterServiceImpl(
                        userRepository,
                        authenticationMapper,
                        roleRepository,
                        passwordEncoder,
                        teacherRepository,
                        studentRepository,
                        jwtService);
        getRolesServiceImpl = new GetRolesServiceImpl(roleRepository, authenticationMapper);
        loginServiceImpl =
                new LoginServiceImpl(
                        roleRepository,
                        authenticationMapper,
                        userRepository,
                        passwordEncoder,
                        authenticationManager,
                        jwtService);
    }
}

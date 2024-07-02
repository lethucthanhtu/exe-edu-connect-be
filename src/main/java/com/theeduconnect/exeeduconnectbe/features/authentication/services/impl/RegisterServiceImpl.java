package com.theeduconnect.exeeduconnectbe.features.authentication.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.AuthenticationMapper;
import com.theeduconnect.exeeduconnectbe.constants.authentication.*;
import com.theeduconnect.exeeduconnectbe.domain.Role;
import com.theeduconnect.exeeduconnectbe.domain.Student;
import com.theeduconnect.exeeduconnectbe.domain.Teacher;
import com.theeduconnect.exeeduconnectbe.domain.User;
import com.theeduconnect.exeeduconnectbe.features.authentication.payload.request.RegisterRequest;
import com.theeduconnect.exeeduconnectbe.features.authentication.payload.response.AuthenticationServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.authentication.services.JwtService;
import com.theeduconnect.exeeduconnectbe.repositories.RoleRepository;
import com.theeduconnect.exeeduconnectbe.repositories.StudentRepository;
import com.theeduconnect.exeeduconnectbe.repositories.TeacherRepository;
import com.theeduconnect.exeeduconnectbe.repositories.UserRepository;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;

public class RegisterServiceImpl {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final AuthenticationMapper authenticationMapper;
    private final JwtService jwtService;
    private RegisterRequest request;
    private User user;
    private Role role;
    private String jwtToken;

    public RegisterServiceImpl(
            UserRepository userRepository,
            AuthenticationMapper authenticationMapper,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            TeacherRepository teacherRepository,
            StudentRepository studentRepository,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.authenticationMapper = authenticationMapper;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.jwtService = jwtService;
    }

    public AuthenticationServiceResponse Handle(RegisterRequest request) {
        try {
            this.request = request;
            if (IsEmailTaken()) return EmailIsTakenResult();
            if (!IsRoleValid()) return InvalidRoleResult();
            MapDtoToUserEntity();
            userRepository.save(user);
            ConvertRoleToSpecificUser();
            if (IsRoleTeacher()) {
                HandlePostRegistrationForTeacher();
                return SuccessfulRegistrationForTeacherResult();
            }
            return SuccessfulRegistrationResult();
        } catch (Exception e) {
            return InternalServerErrorResult(e);
        }
    }

    private boolean IsEmailTaken() {
        Optional<User> userOptional = userRepository.findUserByEmail(request.getEmail());
        if (userOptional.isEmpty()) return false;
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
        user.setFullname(request.getFullname());
        user.setProvider(ProviderEnum.LOCAL);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUsername(RegistrationConstants.SAMPLE_USER_NAME);
        user.setDateofbirth(LocalDate.of(2024, 1, 1));
        user.setAvatarurl(RegistrationConstants.AVATAR_URL);
        user.setPhone(RegistrationConstants.SAMPLE_PHONE_NUMBER);
        user.setAddress(RegistrationConstants.SAMPLE_ADDRESS);
        user.setStatus(true);
        user.setBalance(0.0);
        user.setResetPasswordToken("");
    }

    private void ConvertRoleToSpecificUser() {
        int roleId = role.getId();
        if (roleId == AuthenticationRoles.STUDENT_INT) CreateNewStudent();
        else if (roleId == AuthenticationRoles.TEACHER_INT) CreateNewTeacher();
    }

    private boolean IsRoleTeacher() {
        int roleId = role.getId();
        if (roleId == AuthenticationRoles.TEACHER_INT) return true;
        return false;
    }

    private void HandlePostRegistrationForTeacher() {
        User newlyRegisteredTeacher = userRepository.findUserByEmail(request.getEmail()).get();
        jwtToken = BuildJwtTokenForNewlyRegisteredTeacher(newlyRegisteredTeacher);
    }

    private String BuildJwtTokenForNewlyRegisteredTeacher(User newlyRegisteredTeacher) {
        Map<String, Object> userClaims = new HashMap<>();
        userClaims.put("userId", newlyRegisteredTeacher.getId());
        return jwtService.generateToken(userClaims, user);
    }

    private void CreateNewStudent() {
        Student student = new Student();
        student.setUser(user);
        studentRepository.save(student);
    }

    private void CreateNewTeacher() {
        Teacher teacher = new Teacher();
        teacher.setUser(user);
        teacherRepository.save(teacher);
    }

    private AuthenticationServiceResponse EmailIsTakenResult() {
        return new AuthenticationServiceResponse(
                AuthenticationHttpResponseCodes.EMAIL_IS_TAKEN,
                AuthenticationServiceMessages.EMAIL_IS_TAKEN,
                null);
    }

    private AuthenticationServiceResponse InvalidRoleResult() {
        return new AuthenticationServiceResponse(
                AuthenticationHttpResponseCodes.INVALID_ROLE_RESULT,
                AuthenticationServiceMessages.INVALID_ROLE_RESULT,
                null);
    }

    private AuthenticationServiceResponse SuccessfulRegistrationResult() {
        return new AuthenticationServiceResponse(
                AuthenticationHttpResponseCodes.SUCCESSFUL_REGISTRATION,
                AuthenticationServiceMessages.SUCCESSFUL_REGISTRATION,
                null);
    }

    private AuthenticationServiceResponse SuccessfulRegistrationForTeacherResult() {
        return new AuthenticationServiceResponse(
                AuthenticationHttpResponseCodes.SUCCESSFUL_REGISTRATION,
                AuthenticationServiceMessages.SUCCESSFUL_REGISTRATION,
                jwtToken);
    }

    private AuthenticationServiceResponse InternalServerErrorResult(Exception e) {
        return new AuthenticationServiceResponse(
                AuthenticationHttpResponseCodes.INTERNAL_SERVER_ERROR,
                AuthenticationServiceMessages.INTERNAL_SERVER_ERROR,
                e.getMessage());
    }
}

package com.theeduconnect.exeeduconnectbe.features.user.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.UserMapper;
import com.theeduconnect.exeeduconnectbe.constants.user.UserServiceHttpResponseCodes;
import com.theeduconnect.exeeduconnectbe.constants.user.UserServiceMessages;
import com.theeduconnect.exeeduconnectbe.domain.Role;
import com.theeduconnect.exeeduconnectbe.domain.User;
import com.theeduconnect.exeeduconnectbe.features.user.payload.request.ChangePasswordRequest;
import com.theeduconnect.exeeduconnectbe.features.user.payload.request.NewUserRequest;
import com.theeduconnect.exeeduconnectbe.features.user.payload.response.UserServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.user.services.UserService;
import com.theeduconnect.exeeduconnectbe.repositories.RoleRepository;
import com.theeduconnect.exeeduconnectbe.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired private JavaMailSender mailSender;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            UserMapper userMapper,
            BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserServiceResponse getAllUsers() {
        List<User> users = userRepository.findAll();
        return new UserServiceResponse(
                UserServiceHttpResponseCodes.FOUND_ALL_USER,
                UserServiceMessages.FOUND_ALL_USER,
                users);
    }

    @Override
    public UserServiceResponse createUser(NewUserRequest request) {
        User user = userMapper.newUserRequestToUserEntity(request);
        Optional<Role> roleOptional = roleRepository.findById(request.getRoleId());
        if (roleOptional.isPresent()) {
            user.setRole(roleOptional.get());
            userRepository.save(user);
            return new UserServiceResponse(
                    UserServiceHttpResponseCodes.CREATE_USER_SUCCESSFUL,
                    UserServiceMessages.CREATE_USER_SUCCESSFUL,
                    null);
        }
        return new UserServiceResponse(
                UserServiceHttpResponseCodes.ROLE_NOT_FOUND,
                UserServiceMessages.ROLE_NOT_FOUND,
                null);
    }

    @Override
    public UserServiceResponse getUserById(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            return new UserServiceResponse(
                    UserServiceHttpResponseCodes.GET_USER_BY_ID_SUCCESSFUL,
                    UserServiceMessages.GET_USER_BY_ID_SUCCESSFUL,
                    userOptional.get());
        }
        return new UserServiceResponse(
                UserServiceHttpResponseCodes.USER_NOT_FOUND,
                UserServiceMessages.USER_NOT_FOUND,
                null);
    }

    @Override
    public UserServiceResponse updateUser(int userId, NewUserRequest request) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(request.getUsername());
            user.setFullname(request.getFullName());
            user.setDateofbirth(request.getDateOfBirth());
            user.setAvatarurl(request.getAvatarUrl());
            user.setEmail(request.getEmail());
            user.setPhone(request.getPhone());
            //            user.setPassword(request.getPassword());
            user.setAddress(request.getAddress());
            user.setStatus(request.getStatus());
            user.setBalance(request.getBalance());
            Optional<Role> roleOptional = roleRepository.findById(request.getRoleId());
            if (roleOptional.isPresent()) {
                user.setRole(roleOptional.get());
                userRepository.save(user);
                return new UserServiceResponse(
                        UserServiceHttpResponseCodes.UPDATED_USER_SUCCESSFUL,
                        UserServiceMessages.UPDATED_USER_SUCCESSFUL,
                        null);
            }
            return new UserServiceResponse(
                    UserServiceHttpResponseCodes.ROLE_NOT_FOUND,
                    UserServiceMessages.ROLE_NOT_FOUND,
                    null);
        }
        return new UserServiceResponse(
                UserServiceHttpResponseCodes.USER_NOT_FOUND,
                UserServiceMessages.USER_NOT_FOUND,
                null);
    }

    @Override
    public UserServiceResponse deleteUser(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return new UserServiceResponse(
                    UserServiceHttpResponseCodes.DELETED_USER_SUCCESSFUL,
                    UserServiceMessages.DELETED_USER_SUCCESSFUL,
                    null);
        }
        return new UserServiceResponse(
                UserServiceHttpResponseCodes.USER_NOT_FOUND,
                UserServiceMessages.USER_NOT_FOUND,
                null);
    }

    @Override
    public UserServiceResponse changePassword(int userId, ChangePasswordRequest request) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Check if the current password is correct
            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                return new UserServiceResponse(
                        UserServiceHttpResponseCodes.CURRENT_PASSWORD_INCORRECT,
                        UserServiceMessages.CURRENT_PASSWORD_INCORRECT,
                        null);
            }
            // Update the password
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);

            return new UserServiceResponse(
                    UserServiceHttpResponseCodes.CHANGE_PASSWORD_SUCCESSFUL,
                    UserServiceMessages.CHANGE_PASSWORD_SUCCESSFUL,
                    null);
        }

        return new UserServiceResponse(
                UserServiceHttpResponseCodes.USER_NOT_FOUND,
                UserServiceMessages.USER_NOT_FOUND,
                null);
    }

    @Override
    public UserServiceResponse sendResetPasswordEmail(String email) {
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String token = UUID.randomUUID().toString();
            user.setResetPasswordToken(token);
            userRepository.save(user);

            String resetLink = "http://localhost:8082/api/users/reset-password?token=" + token;
            sendEmail(user.getEmail(), resetLink);

            return new UserServiceResponse(
                    UserServiceHttpResponseCodes.RESET_PASSWORD_EMAIL_SENT,
                    UserServiceMessages.RESET_PASSWORD_EMAIL_SENT,
                    null);
        } else {
            return new UserServiceResponse(
                    UserServiceHttpResponseCodes.USER_NOT_FOUND,
                    UserServiceMessages.USER_NOT_FOUND,
                    null);
        }
    }

    private void sendEmail(String to, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Reset your password");
        message.setText("Click the link to reset your password: " + resetLink);
        mailSender.send(message);
    }

    @Override
    public UserServiceResponse resetPassword(String token, String newPassword) {
        Optional<User> userOptional = userRepository.findByResetPasswordToken(token);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setResetPasswordToken(null);
            userRepository.save(user);
            return new UserServiceResponse(
                    UserServiceHttpResponseCodes.RESET_PASSWORD_SUCCESSFUL,
                    UserServiceMessages.RESET_PASSWORD_SUCCESSFUL,
                    null);
        } else {
            return new UserServiceResponse(
                    UserServiceHttpResponseCodes.INVALID_RESET_PASSWORD_TOKEN,
                    UserServiceMessages.INVALID_RESET_PASSWORD_TOKEN,
                    null);
        }
    }
}

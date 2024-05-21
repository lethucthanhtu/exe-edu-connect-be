package com.theeduconnect.exeeduconnectbe.features.user.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.UserMapper;
import com.theeduconnect.exeeduconnectbe.constants.user.responseCodes.UserServiceHttpResponseCodes;
import com.theeduconnect.exeeduconnectbe.constants.user.serviceMessages.UserServiceMessages;
import com.theeduconnect.exeeduconnectbe.domain.entities.Role;
import com.theeduconnect.exeeduconnectbe.domain.entities.User;
import com.theeduconnect.exeeduconnectbe.features.user.payload.request.NewUserRequest;
import com.theeduconnect.exeeduconnectbe.features.user.payload.response.UserServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.user.services.UserService;
import com.theeduconnect.exeeduconnectbe.repositories.RoleRepository;
import com.theeduconnect.exeeduconnectbe.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserServiceResponse getAllUsers() {
        List<User> users = userRepository.findAll();
        return new UserServiceResponse(UserServiceHttpResponseCodes.FOUND_ALL_USER, UserServiceMessages.FOUND_ALL_USER, users);
    }

    @Override
    public UserServiceResponse createUser(NewUserRequest request) {
        User user = userMapper.newUserRequestToUserEntity(request);
        Optional<Role> roleOptional = roleRepository.findById(request.getRoleId());
        if (roleOptional.isPresent()) {
            user.setRole(roleOptional.get());
            userRepository.save(user);
            return new UserServiceResponse(UserServiceHttpResponseCodes.CREATE_USER_SUCCESSFUL, UserServiceMessages.CREATE_USER_SUCCESSFUL, null);
        }
        return new UserServiceResponse(UserServiceHttpResponseCodes.ROLE_NOT_FOUND, UserServiceMessages.ROLE_NOT_FOUND, null);
    }

    @Override
    public UserServiceResponse getUserById(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            return new UserServiceResponse(UserServiceHttpResponseCodes.GET_USER_BY_ID_SUCCESSFUL, UserServiceMessages.GET_USER_BY_ID_SUCCESSFUL, userOptional.get());
        }
        return new UserServiceResponse(UserServiceHttpResponseCodes.USER_NOT_FOUND, UserServiceMessages.USER_NOT_FOUND, null);
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
            user.setPassword(request.getPassword());
            user.setAddress(request.getAddress());
            user.setStatus(request.getStatus());
            user.setBalance(request.getBalance());
            Optional<Role> roleOptional = roleRepository.findById(request.getRoleId());
            if (roleOptional.isPresent()) {
                user.setRole(roleOptional.get());
                userRepository.save(user);
                return new UserServiceResponse(UserServiceHttpResponseCodes.UPDATED_USER_SUCCESSFUL, UserServiceMessages.UPDATED_USER_SUCCESSFUL, null);
            }
            return new UserServiceResponse(UserServiceHttpResponseCodes.ROLE_NOT_FOUND, UserServiceMessages.ROLE_NOT_FOUND, null);
        }
        return new UserServiceResponse(UserServiceHttpResponseCodes.USER_NOT_FOUND, UserServiceMessages.USER_NOT_FOUND, null);
    }

    @Override
    public UserServiceResponse deleteUser(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return new UserServiceResponse(UserServiceHttpResponseCodes.DELETED_USER_SUCCESSFUL, UserServiceMessages.DELETED_USER_SUCCESSFUL, null);
        }
        return new UserServiceResponse(UserServiceHttpResponseCodes.USER_NOT_FOUND, UserServiceMessages.USER_NOT_FOUND, null);
    }
}

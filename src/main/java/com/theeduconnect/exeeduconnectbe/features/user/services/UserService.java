package com.theeduconnect.exeeduconnectbe.features.user.services;

import com.theeduconnect.exeeduconnectbe.features.user.payload.request.NewUserRequest;
import com.theeduconnect.exeeduconnectbe.features.user.payload.response.UserServiceResponse;

public interface UserService {
    UserServiceResponse getAllUsers();

    UserServiceResponse createUser(NewUserRequest request);

    UserServiceResponse getUserById(int userId);

    UserServiceResponse updateUser(int userId, NewUserRequest request);

    UserServiceResponse deleteUser(int userId);
}

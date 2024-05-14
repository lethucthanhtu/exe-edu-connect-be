/* (C) 2024 */
package com.theeduconnect.exeeduconnectbe.features.authentication.services;

import com.theeduconnect.exeeduconnectbe.features.authentication.payload.request.LoginRequest;
import com.theeduconnect.exeeduconnectbe.features.authentication.payload.response.AuthenticationServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.authentication.payload.request.RegisterRequest;

public interface AuthenticationService {
    AuthenticationServiceResponse Register(RegisterRequest registerRequest);
    AuthenticationServiceResponse Login(LoginRequest loginRequest);
    AuthenticationServiceResponse GetRoles();
}

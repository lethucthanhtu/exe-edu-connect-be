package com.theeduconnect.exeeduconnectbe.features.user.controllers;

import com.theeduconnect.exeeduconnectbe.constants.user.endpoints.UserEndpoints;
import com.theeduconnect.exeeduconnectbe.features.authentication.services.JwtService;
import com.theeduconnect.exeeduconnectbe.features.user.payload.request.ChangePasswordRequest;
import com.theeduconnect.exeeduconnectbe.features.user.payload.request.NewUserRequest;
import com.theeduconnect.exeeduconnectbe.features.user.payload.request.ResetPasswordRequest;
import com.theeduconnect.exeeduconnectbe.features.user.payload.response.UserServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.user.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UserEndpoints.GET_ALL_USER)
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping
    @Operation(summary = "Get all users.")
    public ResponseEntity<UserServiceResponse> getAllUsers() {
        UserServiceResponse response = userService.getAllUsers();
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    //    @PostMapping
    //    @Operation(summary = "Create a new user.")
    //    public ResponseEntity<UserServiceResponse> createUser(@RequestBody NewUserRequest request)
    // {
    //        UserServiceResponse response = userService.createUser(request);
    //        return new ResponseEntity<>(response,
    // HttpStatusCode.valueOf(response.getStatusCode()));
    //    }

    @GetMapping(UserEndpoints.GET_USER_BY_ID)
    @Operation(summary = "Get a user by Id.")
    public ResponseEntity<UserServiceResponse> getUserById(@PathVariable int userId) {
        UserServiceResponse response = userService.getUserById(userId);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping(UserEndpoints.GET_USER_BY_JWT)
    @Operation(summary = "Get a user by JWT.")
    public ResponseEntity<UserServiceResponse> getUserById(@RequestHeader("Authorization") String rawJwtToken) {
        int userId = jwtService.extractUserId(rawJwtToken);
        UserServiceResponse response = userService.getUserById(userId);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PutMapping(UserEndpoints.UPDATE_USER)
    @Operation(summary = "Update a user by Id.")
    public ResponseEntity<UserServiceResponse> updateUser(
            @RequestHeader("Authorization") String rawJwtToken, @Valid @RequestBody NewUserRequest request) {
        int userId = jwtService.extractUserId(rawJwtToken);
        UserServiceResponse response = userService.updateUser(userId, request);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @DeleteMapping(UserEndpoints.DELETE_USER)
    @Operation(summary = "Delete a user by Id")
    public ResponseEntity<UserServiceResponse> deleteUser(@PathVariable int userId) {
        UserServiceResponse response = userService.deleteUser(userId);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PostMapping(UserEndpoints.CHANGE_PASSWORD)
    @Operation(summary = "Change password")
    public ResponseEntity<UserServiceResponse> changePassword(
            @RequestHeader("Authorization") String rawJwtToken, @Valid @RequestBody ChangePasswordRequest request) {
        // You may want to add additional security checks here
        int userId = jwtService.extractUserId(rawJwtToken);
        UserServiceResponse response = userService.changePassword(userId, request);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PostMapping(UserEndpoints.REQUEST_RESET_PASSWORD)
    @Operation(summary = "Request to reset password")
    public ResponseEntity<UserServiceResponse> requestResetPassword(
            @RequestBody ResetPasswordRequest resetPasswordRequest) {
        UserServiceResponse response =
                userService.sendResetPasswordEmail(resetPasswordRequest.getEmail());
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PostMapping(UserEndpoints.RESET_PASSWORD)
    @Operation(summary = "Reset password")
    public ResponseEntity<UserServiceResponse> resetPassword(
            @Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        UserServiceResponse response =
                userService.resetPassword(
                        resetPasswordRequest.getToken(), resetPasswordRequest.getNewPassword());
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }
}

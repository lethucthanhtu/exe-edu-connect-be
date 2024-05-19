package com.theeduconnect.exeeduconnectbe.features.user.controllers;

import com.theeduconnect.exeeduconnectbe.constants.user.endpoints.UserEndpoints;
import com.theeduconnect.exeeduconnectbe.features.user.payload.request.NewUserRequest;
import com.theeduconnect.exeeduconnectbe.features.user.payload.response.UserServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.user.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UserEndpoints.GET_ALL_USER)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Get all users.")
    public ResponseEntity<UserServiceResponse> getAllUsers() {
        UserServiceResponse response = userService.getAllUsers();
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PostMapping
    @Operation(summary = "Create a new user.")
    public ResponseEntity<UserServiceResponse> createUser(@RequestBody NewUserRequest request) {
        UserServiceResponse response = userService.createUser(request);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping(UserEndpoints.GET_USER_BY_ID)
    @Operation(summary = "Get a user by Id.")
    public ResponseEntity<UserServiceResponse> getUserById(@PathVariable int userId) {
        UserServiceResponse response = userService.getUserById(userId);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PutMapping(UserEndpoints.UPDATE_USER)
    @Operation(summary = "Update a user by Id.")
    public ResponseEntity<UserServiceResponse> updateUser(
            @PathVariable int userId, @RequestBody NewUserRequest request) {
        UserServiceResponse response = userService.updateUser(userId, request);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @DeleteMapping(UserEndpoints.DELETE_USER)
    @Operation(summary = "Delete a user by Id")
    public ResponseEntity<UserServiceResponse> deleteUser(@PathVariable int userId) {
        UserServiceResponse response = userService.deleteUser(userId);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }
}

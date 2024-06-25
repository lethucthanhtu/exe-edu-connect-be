package com.theeduconnect.exeeduconnectbe.features.attendingCourse.controllers;

import com.theeduconnect.exeeduconnectbe.constants.attendingCourse.AttendingCourseEndpoints;
import com.theeduconnect.exeeduconnectbe.features.attendingCourse.payload.response.AttendingCourseServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.attendingCourse.services.AttendingCourseService;
import com.theeduconnect.exeeduconnectbe.features.authentication.services.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AttendingCourseController {

    private final AttendingCourseService attendingCourseService;
    private final JwtService jwtService;

    @Autowired
    public AttendingCourseController(
            AttendingCourseService attendingCourseService, JwtService jwtService) {
        this.attendingCourseService = attendingCourseService;
        this.jwtService = jwtService;
    }

    @GetMapping(AttendingCourseEndpoints.GET_ALL_BY_STUDENT_ID)
    @Operation(summary = "Gets a list of AttendingCourses, based on the given user Id.")
    public ResponseEntity<AttendingCourseServiceResponse> GetCoursesWithPagination(
            @RequestHeader("Authorization") String rawJwtToken) {
        int studentId = jwtService.extractUserId(rawJwtToken);
        AttendingCourseServiceResponse response =
                attendingCourseService.getAllByStudentId(studentId);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }
}

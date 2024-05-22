package com.theeduconnect.exeeduconnectbe.features.teacher.controllers;

import com.theeduconnect.exeeduconnectbe.constants.teacher.endpoints.TeacherEndpoints;
import com.theeduconnect.exeeduconnectbe.features.authentication.services.JwtService;
import com.theeduconnect.exeeduconnectbe.features.teacher.payload.request.GetAllTeachersByRequest;
import com.theeduconnect.exeeduconnectbe.features.teacher.payload.response.TeacherServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.teacher.services.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeacherController {

    private final TeacherService teacherService;
    private final JwtService jwtService;

    @Autowired
    public TeacherController(TeacherService teacherService, JwtService jwtService) {
        this.teacherService = teacherService;
        this.jwtService = jwtService;
    }

    @GetMapping(TeacherEndpoints.GET_ALL_BY_COURSE_CATEGORY)
    @Operation(summary = "Gets a list of Teachers, based on the specified Course Category.")
    public ResponseEntity<TeacherServiceResponse> GetTeachersByCourseCategory(
            @RequestParam(value = "category") String category,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        GetAllTeachersByRequest getAllByRequest = new GetAllTeachersByRequest(category, page, size);
        TeacherServiceResponse response = teacherService.getAllByRequest(getAllByRequest);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }
}

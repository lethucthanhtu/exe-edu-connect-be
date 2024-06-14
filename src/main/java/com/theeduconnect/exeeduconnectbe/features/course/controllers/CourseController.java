package com.theeduconnect.exeeduconnectbe.features.course.controllers;

import com.theeduconnect.exeeduconnectbe.constants.course.CourseEndpoints;
import com.theeduconnect.exeeduconnectbe.features.authentication.services.JwtService;
import com.theeduconnect.exeeduconnectbe.features.course.payload.request.GetAllCoursesByRequest;
import com.theeduconnect.exeeduconnectbe.features.course.payload.request.JoinCourseRequest;
import com.theeduconnect.exeeduconnectbe.features.course.payload.request.NewCourseRequest;
import com.theeduconnect.exeeduconnectbe.features.course.payload.response.CourseServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.course.services.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CourseController {

    private final CourseService courseService;
    private final JwtService jwtService;

    @Autowired
    public CourseController(CourseService courseService, JwtService jwtService) {
        this.courseService = courseService;
        this.jwtService = jwtService;
    }

    @GetMapping(CourseEndpoints.GET_ALL_BY)
    @Operation(summary = "Gets a list of Course, with additional parameters.")
    public ResponseEntity<CourseServiceResponse> GetCoursesWithPagination(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        GetAllCoursesByRequest getAllCoursesByRequest =
                new GetAllCoursesByRequest(name, category, page, size);
        CourseServiceResponse response = courseService.getAllByRequest(getAllCoursesByRequest);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping(CourseEndpoints.GET_BY_ID)
    @Operation(summary = "Gets full details of a Course, based on its Id.")
    public ResponseEntity<CourseServiceResponse> GetCourseById(
            @PathVariable(value = "id", required = false) int id) {
        CourseServiceResponse response = courseService.getById(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PostMapping(CourseEndpoints.CREATE)
    @Operation(summary = "Creates a new Course.")
    public ResponseEntity<CourseServiceResponse> CreateNewCourse(
            @RequestHeader("Authorization") String rawJwtToken,
            @Valid @RequestBody NewCourseRequest newCourseRequest) {
        int userId = jwtService.extractUserId(rawJwtToken);
        newCourseRequest.setTeacherid(userId);
        CourseServiceResponse response = courseService.create(newCourseRequest);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PostMapping(CourseEndpoints.JOIN_COURSE)
    @Operation(summary = "Lets a student join a Course, based on the schedules of that course.")
    public ResponseEntity<CourseServiceResponse> JoinACourse(
            @RequestHeader("Authorization") String rawJwtToken,
            @Valid @RequestBody JoinCourseRequest request) {
        int userId = jwtService.extractUserId(rawJwtToken);
        request.setStudentId(userId);
        CourseServiceResponse response = courseService.join(request);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }
}

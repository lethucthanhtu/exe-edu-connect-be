package com.theeduconnect.exeeduconnectbe.features.course.controllers;

import com.theeduconnect.exeeduconnectbe.constants.course.endpoints.CourseEndpoints;
import com.theeduconnect.exeeduconnectbe.features.course.payload.request.NewCourseRequest;
import com.theeduconnect.exeeduconnectbe.features.course.payload.request.PaginationRequest;
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

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(CourseEndpoints.GET_ALL)
    @Operation(summary = "Gets a list of Course, with Pagination.")
    public ResponseEntity<CourseServiceResponse> GetCoursesWithPagination(
            @RequestParam("page") int page, @RequestParam("size") int size) {
        PaginationRequest paginationRequest = new PaginationRequest(page, size);
        CourseServiceResponse response = courseService.getAllWithPagination(paginationRequest);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PostMapping(CourseEndpoints.CREATE)
    @Operation(summary = "Creates a new Course.")
    public ResponseEntity<CourseServiceResponse> CreateNewCourse(
            @Valid @RequestBody NewCourseRequest newCourseRequest) {
        CourseServiceResponse response = courseService.create(newCourseRequest);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }
}

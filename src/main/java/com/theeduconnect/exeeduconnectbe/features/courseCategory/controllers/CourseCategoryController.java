package com.theeduconnect.exeeduconnectbe.features.courseCategory.controllers;

import com.theeduconnect.exeeduconnectbe.constants.courseCategory.endpoints.CourseCategoryEndpoints;
import com.theeduconnect.exeeduconnectbe.features.authentication.services.JwtService;
import com.theeduconnect.exeeduconnectbe.features.courseCategory.payload.response.CourseCategoryServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.courseCategory.services.CourseCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CourseCategoryController {

    private final CourseCategoryService courseCategoryService;

    @Autowired
    public CourseCategoryController(
            CourseCategoryService courseCategoryService) {
        this.courseCategoryService = courseCategoryService;
    }

    @GetMapping(CourseCategoryEndpoints.GET_ALL_URL)
    @Operation(summary = "Gets a list of Course Categories")
    public ResponseEntity<CourseCategoryServiceResponse> GetAllCourseCategories() {
        CourseCategoryServiceResponse response = courseCategoryService.getAll();
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }
    @GetMapping(CourseCategoryEndpoints.GET_BY_ID_URL)
    @Operation(summary = "Gets a Course Category based on the given name.")
    public ResponseEntity<CourseCategoryServiceResponse> GetCourseCategoryByName(
            @PathVariable(name = "name") String categoryName) {
        CourseCategoryServiceResponse response = courseCategoryService.getByName(categoryName);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }
}

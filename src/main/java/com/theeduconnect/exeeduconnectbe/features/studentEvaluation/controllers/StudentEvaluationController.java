package com.theeduconnect.exeeduconnectbe.features.studentEvaluation.controllers;

import com.theeduconnect.exeeduconnectbe.constants.studentEvaluation.StudentEvaluationEndpoints;
import com.theeduconnect.exeeduconnectbe.features.studentEvaluation.payload.request.StudentEvaluationRequest;
import com.theeduconnect.exeeduconnectbe.features.studentEvaluation.payload.response.StudentEvaluationResponse;
import com.theeduconnect.exeeduconnectbe.features.studentEvaluation.services.StudentEvaluationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(StudentEvaluationEndpoints.STUDENT_EVALUATION_ROOT)
public class StudentEvaluationController {

    private final StudentEvaluationService studentEvaluationService;

    @Autowired
    public StudentEvaluationController(StudentEvaluationService studentEvaluationService) {
        this.studentEvaluationService = studentEvaluationService;
    }

    @GetMapping
    @Operation(summary = "Get all student evaluations")
    public ResponseEntity<StudentEvaluationResponse> getAllEvaluations() {
        StudentEvaluationResponse response = studentEvaluationService.getAllEvaluations();
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping(StudentEvaluationEndpoints.GET_ALL_EVALUATION_BY_STUDENT_ID)
    @Operation(summary = "Get all evaluations of a student by student Id")
    public ResponseEntity<StudentEvaluationResponse> getAllEvaluationsByStudentId(
            @PathVariable int studentId) {
        StudentEvaluationResponse response =
                studentEvaluationService.getAllEvaluationsByStudentId(studentId);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping(StudentEvaluationEndpoints.GET_ALL_EVALUATION_BY_STUDENTID_AND_COURSEID)
    @Operation(summary = "Get all evaluations of a course by studentId & courseId")
    public ResponseEntity<StudentEvaluationResponse> getEvaluationsByStudentIdAndCourseId(
            @PathVariable int studentId, @PathVariable int courseId) {
        StudentEvaluationResponse response =
                studentEvaluationService.getEvaluationsByStudentIdAndCourseId(studentId, courseId);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping(StudentEvaluationEndpoints.GET_EVALUATION_BY_ID)
    @Operation(summary = "Get a student evaluation by Id")
    public ResponseEntity<StudentEvaluationResponse> getEvaluationById(@PathVariable int id) {
        StudentEvaluationResponse response = studentEvaluationService.getEvaluationById(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PostMapping
    @Operation(summary = "Create a new student evaluation")
    public ResponseEntity<StudentEvaluationResponse> createEvaluation(
            @Valid @RequestBody StudentEvaluationRequest request) {
        StudentEvaluationResponse response = studentEvaluationService.createEvaluation(request);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PutMapping(StudentEvaluationEndpoints.UPDATE_EVALUATION_BY_ID)
    @Operation(summary = "Update a student evaluation by Id")
    public ResponseEntity<StudentEvaluationResponse> updateEvaluation(
            @PathVariable int id, @Valid @RequestBody StudentEvaluationRequest request) {
        StudentEvaluationResponse response = studentEvaluationService.updateEvaluation(id, request);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @DeleteMapping(StudentEvaluationEndpoints.DELETE_EVALUATION_BY_ID)
    @Operation(summary = "Delete a student evaluation by Id")
    public ResponseEntity<StudentEvaluationResponse> deleteEvaluation(@PathVariable int id) {
        StudentEvaluationResponse response = studentEvaluationService.deleteEvaluation(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }
}

package com.theeduconnect.exeeduconnectbe.features.studentEvaluation.controllers;

import com.theeduconnect.exeeduconnectbe.features.studentEvaluation.payload.request.StudentEvaluationRequest;
import com.theeduconnect.exeeduconnectbe.features.studentEvaluation.services.StudentEvaluationService;
import com.theeduconnect.exeeduconnectbe.features.studentEvaluation.payload.response.StudentEvaluationResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student-evaluations")
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

    @GetMapping("/{id}")
    @Operation(summary = "Get a student evaluation by Id")
    public ResponseEntity<StudentEvaluationResponse> getEvaluationById(@PathVariable int id) {
        StudentEvaluationResponse response = studentEvaluationService.getEvaluationById(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PostMapping
    @Operation(summary = "Create a new student evaluation")
    public ResponseEntity<StudentEvaluationResponse> createEvaluation(@RequestBody StudentEvaluationRequest request) {
        StudentEvaluationResponse response = studentEvaluationService.createEvaluation(request);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a student evaluation by Id")
    public ResponseEntity<StudentEvaluationResponse> updateEvaluation(
            @PathVariable int id, @RequestBody StudentEvaluationRequest request) {
        StudentEvaluationResponse response = studentEvaluationService.updateEvaluation(id, request);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a student evaluation by Id")
    public ResponseEntity<StudentEvaluationResponse> deleteEvaluation(@PathVariable int id) {
        StudentEvaluationResponse response = studentEvaluationService.deleteEvaluation(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }
}

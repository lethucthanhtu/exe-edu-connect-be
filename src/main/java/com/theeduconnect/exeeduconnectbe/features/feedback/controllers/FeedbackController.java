package com.theeduconnect.exeeduconnectbe.features.feedback.controllers;

import com.theeduconnect.exeeduconnectbe.constants.feedback.endpoints.FeedbackEndpoints;
import com.theeduconnect.exeeduconnectbe.features.authentication.services.JwtService;
import com.theeduconnect.exeeduconnectbe.features.feedback.payload.request.NewFeedbackRequest;
import com.theeduconnect.exeeduconnectbe.features.feedback.payload.response.FeedbackServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.feedback.services.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FeedbackController {
    private JwtService jwtService;
    private FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService, JwtService jwtService) {
        this.feedbackService = feedbackService;
        this.jwtService = jwtService;
    }

    @PostMapping(FeedbackEndpoints.CREATE_COURSE_FEEDBACK_URL)
    @Operation(summary = "Let a student create a feedback for a course.")
    public ResponseEntity<FeedbackServiceResponse> CreateCourseFeedback(
            @RequestHeader("Authorization") String rawJwtToken,
            @Valid @RequestBody NewFeedbackRequest request) {
        int studentId = jwtService.extractUserId(rawJwtToken);
        request.setStudentid(studentId);
        FeedbackServiceResponse response = feedbackService.CreateCourseFeedback(request);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }
}

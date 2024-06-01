package com.theeduconnect.exeeduconnectbe.features.studentEvaluation.services;

import com.theeduconnect.exeeduconnectbe.features.studentEvaluation.payload.request.StudentEvaluationRequest;
import com.theeduconnect.exeeduconnectbe.features.studentEvaluation.payload.response.StudentEvaluationResponse;
import com.theeduconnect.exeeduconnectbe.features.user.payload.response.UserServiceResponse;

import java.util.List;

public interface StudentEvaluationService {
    StudentEvaluationResponse getAllEvaluations();
    StudentEvaluationResponse getEvaluationById(int id);
    StudentEvaluationResponse createEvaluation(StudentEvaluationRequest request);
    StudentEvaluationResponse updateEvaluation(int id, StudentEvaluationRequest request);
    StudentEvaluationResponse deleteEvaluation(int id);
}


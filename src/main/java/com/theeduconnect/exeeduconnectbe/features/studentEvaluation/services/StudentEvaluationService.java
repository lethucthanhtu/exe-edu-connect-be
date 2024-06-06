package com.theeduconnect.exeeduconnectbe.features.studentEvaluation.services;

import com.theeduconnect.exeeduconnectbe.features.studentEvaluation.payload.request.StudentEvaluationRequest;
import com.theeduconnect.exeeduconnectbe.features.studentEvaluation.payload.response.StudentEvaluationResponse;

public interface StudentEvaluationService {
    StudentEvaluationResponse getAllEvaluations();

    StudentEvaluationResponse getAllEvaluationsByStudentId(int studentId);

    StudentEvaluationResponse getEvaluationsByStudentIdAndCourseId(int studentId, int courseId);

    StudentEvaluationResponse getEvaluationById(int id);

    StudentEvaluationResponse createEvaluation(StudentEvaluationRequest request);

    StudentEvaluationResponse updateEvaluation(int id, StudentEvaluationRequest request);

    StudentEvaluationResponse deleteEvaluation(int id);
}

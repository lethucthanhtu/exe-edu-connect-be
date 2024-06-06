package com.theeduconnect.exeeduconnectbe.constants.studentEvaluation;

public class StudentEvaluationEndpoints {
    public static final String STUDENT_EVALUATION_ROOT = "/api/student-evaluations";
    public static final String GET_ALL_EVALUATION_BY_STUDENT_ID = "/student/{studentId}";
    public static final String GET_ALL_EVALUATION_BY_STUDENTID_AND_COURSEID = "/student/{studentId}/course/{courseId}";
    public static final String GET_EVALUATION_BY_ID = "/{id}";
    public static final String UPDATE_EVALUATION_BY_ID = "/{id}";
    public static final String DELETE_EVALUATION_BY_ID = "/{id}";
}

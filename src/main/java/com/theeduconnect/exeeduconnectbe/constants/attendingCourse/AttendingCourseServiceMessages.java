package com.theeduconnect.exeeduconnectbe.constants.attendingCourse;

public class AttendingCourseServiceMessages {
    public static final String GET_ALL_BY_STUDENT_ID_SUCCESSFUL =
            "Successfully retrieved all attending courses for the given student.";
    public static final String INTERNAL_SERVER_ERROR = "Something went wrong on the server side.";
    public static final String STUDENT_HAS_NOT_ATTENDED_COURSE =
            "The student has not attended any course yet. Please attend a course then try again.";
    public static final String ATTENDING_COURSE_NOT_FOUND =
            "The given attendingCourse is unavailable. Please try again.";
    public static final String ATTENDING_COURSE_TRANSACTION_ALREADY_APPROVED =
            "The given attendingCourse " + "has already been approved.";
    public static final String APPROVE_TRANSACTION_SUCCESSFUL =
            "Successfully approved the transaction " + "for the given attendingCourse.";
}

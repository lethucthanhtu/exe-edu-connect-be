package com.theeduconnect.exeeduconnectbe.constants.teacher.serviceMessages;

public class TeacherServiceMessages {
    public static final String FOUND_ALL_TEACHERS =
            "Successfully retrieved all Teachers with the given configuration. ";
    public static final String INTERNAL_SERVER_ERROR = "Something went wrong on the server side.";
    public static final String FOUND_TEACHER_BY_ID =
            "Successfully retrieved the teacher details with the given Id.";
    public static final String TEACHER_NOT_FOUND =
            "The given Id does not match any teacher in the database. Please try again with another"
                    + " Id.";
}

package com.theeduconnect.exeeduconnectbe.constants.course.validation;

public class CourseValidationSpecifications {
    public static final int MIN_NAME_LENGTH = 5;
    public static final int MAX_NAME_LENGTH = 50;
    public static final int MIN_COURSE_DURATION_IN_MINUTES = 60;
    public static final int MAX_COURSE_DURATION_IN_MINUTES = 120;
    public static final int MIN_DESCRIPTION_LENGTH = 5;
    public static final int MAX_DESCRIPTION_LENGTH = 500;
    public static final String VALID_GOOGLE_MEET_URL =
            "^https:\\/\\/meet\\.google\\.com\\/[a-z0-9-]+$";
}

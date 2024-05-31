package com.theeduconnect.exeeduconnectbe.constants.course.validation;

public class CourseValidationMessages {
    public static final String INVALID_COURSE_NAME =
            "The entered course name must not be empty,"
                    + " and should be between 5 to 50 characters long.";
    public static final String INVALID_DESCRIPTION =
            "The course description must not be empty, and should be between 5 to 500 characters"
                    + " long.";
    public static final String INVALID_PRICE = "The price must be in VND, and larger than 0.";
    public static final String INVALID_START_DATE =
            "The start date is not valid. Please try again.";
    public static final String INVALID_END_DATE = "The end date is not valid. Please try again.";
    public static final String INVALID_CATEGORY_ID =
            "The category id chosen is not valid. Please try another one.";
    public static final String INVALID_START_TIME =
            "The input course start time is invalid. Please make sure that it follows this format:"
                    + "2024-05-24T09:00:00.";
    public static final String INVALID_SCHEDULE_REQUESTS =
            "The schedule request list is empty or invalid. Please try again.";
    public static final String INVALID_COURSE_DURATION_IN_MINUTES =
            "The input course duration must be between 60 minutes (1 hour) "
                    + "and 120 minutes (2 hours). Please try again.";
    public static final String INVALID_COURSE_ID =
            "The courseId must be a positive number.";
    public static final String INVALID_COURSE_SCHEDULE_ID =
            "The courseScheduleId must be a positive number.";
}

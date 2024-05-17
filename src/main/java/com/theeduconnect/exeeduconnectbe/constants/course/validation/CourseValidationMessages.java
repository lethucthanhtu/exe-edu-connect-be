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
}

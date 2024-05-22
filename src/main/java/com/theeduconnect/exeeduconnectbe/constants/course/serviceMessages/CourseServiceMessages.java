package com.theeduconnect.exeeduconnectbe.constants.course.serviceMessages;

public class CourseServiceMessages {
    public static final String FOUND_ALL_COURSES =
            "Successfully retrieved all courses with the given pagination configuration.";
    public static final String NO_COURSES_FOUND =
            "No courses found with the given pagination configuration.";
    public static final String CREATE_COURSE_SUCCESSFUL = "Successfully created a course.";
    public static final String START_DATE_BEFORE_END_DATE =
            "The course start date is NOT before the end date. Please adjust either of the dates"
                    + " accordingly.";
    public static final String INTERNAL_SERVER_ERROR = "Something went wrong on the server side.";
    public static final String INVALID_COURSE_CATEGORY_ID =
            "The entered courseCategoryId is non-existent in the system. Please try another one.";
    public static final String INVALID_WEEKDAY = "One of the weekdays might be invalid. " +
            "A valid value should belong to one of the following: MON, TUE, WED, THU, FRI, SAT, SUN.";
}

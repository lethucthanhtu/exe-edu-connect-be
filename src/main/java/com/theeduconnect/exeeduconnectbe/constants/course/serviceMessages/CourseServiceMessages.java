package com.theeduconnect.exeeduconnectbe.constants.course.serviceMessages;

public class CourseServiceMessages {
    public static final String FOUND_ALL_COURSES =
            "Successfully retrieved all courses with the given configuration.";
    public static final String FOUND_COURSE_BY_ID_SUCCESSFUL =
            "Successfully retrieved the course with the given Id.";
    public static final String NO_COURSES_FOUND = "No courses found with the given configuration.";
    public static final String CREATE_COURSE_SUCCESSFUL = "Successfully created a course.";
    public static final String START_DATE_BEFORE_END_DATE =
            "The course start date is NOT before the end date. Please adjust either of the dates"
                    + " accordingly.";
    public static final String INTERNAL_SERVER_ERROR = "Something went wrong on the server side.";
    public static final String INVALID_COURSE_CATEGORY_ID =
            "The entered courseCategoryId is non-existent in the system. Please try another one.";
    public static final String INVALID_COURSE_CATEGORY_NAME =
            "The entered courseCategory Name is non-existent in the system. Please try another"
                    + " one.";
    public static final String INVALID_START_TIME =
            "All Start Times must be between the Start Date and the End Date.";
}

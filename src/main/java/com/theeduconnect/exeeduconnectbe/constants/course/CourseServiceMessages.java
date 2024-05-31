package com.theeduconnect.exeeduconnectbe.constants.course;

public class CourseServiceMessages {
    public static final String FOUND_ALL_COURSES =
            "Successfully retrieved all courses with the given configuration.";
    public static final String FOUND_COURSE_BY_ID_SUCCESSFUL =
            "Successfully retrieved the course with the given Id.";
    public static final String NO_COURSES_FOUND = "No courses found with the given configuration.";
    public static final String CREATE_COURSE_SUCCESSFUL = "Successfully created a course.";
    public static final String JOIN_COURSE_SUCCESSFUL =
            "Successfully added the student to the course schedule.";
    public static final String START_DATE_BEFORE_END_DATE =
            "The course start date is NOT before the end date. Please adjust either of the dates"
                    + " accordingly.";
    public static final String INTERNAL_SERVER_ERROR = "Something went wrong on the server side.";
    public static final String INVALID_COURSE_CATEGORY_ID =
            "The entered courseCategoryId is non-existent in the system. Please try another one.";

    public static final String INVALID_START_TIME =
            "All Start Times must be between the Start Date and the End Date.";
    public static final String COURSE_SCHEDULE_TAKEN =
            "The selected course schedule is already taken by another student. Please choose a"
                    + " different course schedule.";
    public static final String COURSE_SCHEDULE_NOT_FOUND =
            "The given course schedule is not found in the selected class. Plesae choose another"
                    + " course schedule.";
}

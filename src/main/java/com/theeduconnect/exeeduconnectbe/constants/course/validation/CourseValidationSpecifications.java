package com.theeduconnect.exeeduconnectbe.constants.course.validation;

import java.util.Arrays;
import java.util.List;

public class CourseValidationSpecifications {
    public static final int MIN_NAME_LENGTH = 5;
    public static final int MAX_NAME_LENGTH = 50;
    public static final int MIN_DESCRIPTION_LENGTH = 5;
    public static final int MAX_DESCRIPTION_LENGTH = 500;
    public static final List<String> WEEKDAYS =Arrays.asList(
            "MON","TUE","WED","THU","FRI","SAT","SUN");
}

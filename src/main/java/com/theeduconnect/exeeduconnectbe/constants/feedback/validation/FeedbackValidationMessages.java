package com.theeduconnect.exeeduconnectbe.constants.feedback.validation;

public class FeedbackValidationMessages {

    public static final String INVALID_STAR =
            "The input star should be between "
                    + FeedbackValidationSpecifications.MIN_STAR
                    + " to "
                    + FeedbackValidationSpecifications.MAX_STAR
                    + ". Please try again.";
    public static final String INVALID_DESCRIPTION =
            "The input description should be between %d "
                    + FeedbackValidationSpecifications.MIN_DESCRIPTION_LENGTH
                    + "to "
                    + FeedbackValidationSpecifications.MAX_DESCRIPTION_LENGTH
                    + " characters.";
    public static final String INVALID_ATTENDING_COURSE_ID =
            "The attendingcourseid must be a number. Please try again.";
}

package com.theeduconnect.exeeduconnectbe.features.feedback.services;

import com.theeduconnect.exeeduconnectbe.features.feedback.payload.request.NewFeedbackRequest;
import com.theeduconnect.exeeduconnectbe.features.feedback.payload.response.FeedbackServiceResponse;

public interface FeedbackService {
    FeedbackServiceResponse CreateCourseFeedback(NewFeedbackRequest request);
}

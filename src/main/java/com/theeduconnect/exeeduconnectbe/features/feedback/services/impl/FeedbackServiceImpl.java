package com.theeduconnect.exeeduconnectbe.features.feedback.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.FeedbackMapper;
import com.theeduconnect.exeeduconnectbe.features.feedback.payload.request.NewFeedbackRequest;
import com.theeduconnect.exeeduconnectbe.features.feedback.payload.response.FeedbackServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.feedback.services.FeedbackService;
import com.theeduconnect.exeeduconnectbe.repositories.AttendingCourseRepository;
import com.theeduconnect.exeeduconnectbe.repositories.CourseFeedbackRepository;
import com.theeduconnect.exeeduconnectbe.repositories.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final CourseFeedbackRepository courseFeedbackRepository;
    private final StudentRepository studentRepository;
    private final AttendingCourseRepository attendingCourseRepository;
    private final FeedbackMapper feedbackMapper;
    private CreateCourseFeedbackServiceImpl createCourseFeedbackServiceImpl;

    public FeedbackServiceImpl(
            CourseFeedbackRepository courseFeedbackRepository,
            AttendingCourseRepository attendingCourseRepository,
            StudentRepository studentRepository,
            FeedbackMapper feedbackMapper) {
        this.courseFeedbackRepository = courseFeedbackRepository;
        this.attendingCourseRepository = attendingCourseRepository;
        this.studentRepository = studentRepository;
        this.feedbackMapper = feedbackMapper;
        InitializeChildServices();
    }

    @Override
    public FeedbackServiceResponse CreateCourseFeedback(NewFeedbackRequest request) {
        return createCourseFeedbackServiceImpl.Handle(request);
    }

    private void InitializeChildServices() {
        createCourseFeedbackServiceImpl =
                new CreateCourseFeedbackServiceImpl(
                        courseFeedbackRepository,
                        attendingCourseRepository,
                        studentRepository,
                        feedbackMapper);
    }
}

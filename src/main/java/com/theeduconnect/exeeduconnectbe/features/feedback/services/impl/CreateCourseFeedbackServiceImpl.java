package com.theeduconnect.exeeduconnectbe.features.feedback.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.FeedbackMapper;
import com.theeduconnect.exeeduconnectbe.constants.feedback.responseCodes.FeedbackServiceHttpResponseCodes;
import com.theeduconnect.exeeduconnectbe.constants.feedback.serviceMessages.FeedbackServiceMessages;
import com.theeduconnect.exeeduconnectbe.domain.AttendingCourse;
import com.theeduconnect.exeeduconnectbe.domain.CourseFeedback;
import com.theeduconnect.exeeduconnectbe.domain.Student;
import com.theeduconnect.exeeduconnectbe.features.feedback.payload.request.NewFeedbackRequest;
import com.theeduconnect.exeeduconnectbe.features.feedback.payload.response.FeedbackServiceResponse;
import com.theeduconnect.exeeduconnectbe.repositories.AttendingCourseRepository;
import com.theeduconnect.exeeduconnectbe.repositories.CourseFeedbackRepository;
import com.theeduconnect.exeeduconnectbe.repositories.StudentRepository;
import java.time.LocalDate;
import java.util.Optional;

public class CreateCourseFeedbackServiceImpl {
    private final CourseFeedbackRepository courseFeedbackRepository;
    private final AttendingCourseRepository attendingCourseRepository;
    private final FeedbackMapper feedbackMapper;
    private final StudentRepository studentRepository;
    private Student student;
    private NewFeedbackRequest request;
    private CourseFeedback courseFeedback;
    private AttendingCourse attendingCourse;

    public CreateCourseFeedbackServiceImpl(
            CourseFeedbackRepository courseFeedbackRepository,
            AttendingCourseRepository attendingCourseRepository,
            StudentRepository studentRepository,
            FeedbackMapper feedbackMapper) {
        this.courseFeedbackRepository = courseFeedbackRepository;
        this.attendingCourseRepository = attendingCourseRepository;
        this.studentRepository = studentRepository;
        this.feedbackMapper = feedbackMapper;
    }

    public FeedbackServiceResponse Handle(NewFeedbackRequest request) {
        try {
            this.request = request;
            if (!HasStudentAttendedCourse()) return StudentHasNotAttendedCourseResult();
            MapFeedbackRequestToFeedbackEntity();
            courseFeedbackRepository.save(courseFeedback);
            return CreateCourseFeedbackSuccessful();
        } catch (Exception e) {
            return InternalServerErrorResult(e);
        }
    }

    private boolean HasStudentAttendedCourse() {
        student = studentRepository.findById(request.getStudentid()).get();
        Optional<AttendingCourse> attendingCourseOptional =
                attendingCourseRepository.findByStudent(student);
        if (attendingCourseOptional.isEmpty()) return false;
        attendingCourse = attendingCourseOptional.get();
        return true;
    }

    private void MapFeedbackRequestToFeedbackEntity() {
        courseFeedback = feedbackMapper.NewFeedbackRequestToCourseFeedback((request));
        courseFeedback.setPostdate(LocalDate.now());
        courseFeedback.setAttendingcourse(attendingCourse);
    }
    ;

    private FeedbackServiceResponse CreateCourseFeedbackSuccessful() {
        return new FeedbackServiceResponse(
                FeedbackServiceHttpResponseCodes.CREATE_COURSE_FEEDBACK_SUCCESSFUL,
                FeedbackServiceMessages.CREATE_COURSE_FEEDBACK_SUCCESSFUL,
                null);
    }

    private FeedbackServiceResponse StudentHasNotAttendedCourseResult() {
        return new FeedbackServiceResponse(
                FeedbackServiceHttpResponseCodes.STUDENT_HAS_NOT_ATTENDED_COURSE,
                FeedbackServiceMessages.STUDENT_HAS_NOT_ATTENDED_COURSE,
                null);
    }

    private FeedbackServiceResponse InternalServerErrorResult(Exception e) {
        return new FeedbackServiceResponse(
                FeedbackServiceHttpResponseCodes.INTERNAL_SERVER_ERROR,
                FeedbackServiceMessages.INTERNAL_SERVER_ERROR,
                e.getMessage());
    }
}

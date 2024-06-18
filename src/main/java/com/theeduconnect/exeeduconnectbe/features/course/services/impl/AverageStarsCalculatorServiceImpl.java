package com.theeduconnect.exeeduconnectbe.features.course.services.impl;

import com.theeduconnect.exeeduconnectbe.domain.AttendingCourse;
import com.theeduconnect.exeeduconnectbe.domain.Course;
import com.theeduconnect.exeeduconnectbe.domain.CourseFeedback;
import com.theeduconnect.exeeduconnectbe.repositories.AttendingCourseRepository;
import java.util.ArrayList;
import java.util.List;

public class AverageStarsCalculatorServiceImpl {
    private Course course;
    private final AttendingCourseRepository attendingCourseRepository;
    private List<AttendingCourse> attendingCourseList;
    private List<CourseFeedback> courseFeedbackList;
    private double averageStars;

    public AverageStarsCalculatorServiceImpl(AttendingCourseRepository attendingCourseRepository) {
        this.attendingCourseRepository = attendingCourseRepository;
    }

    public double GetAverageStarsByCourseId(Course course) {
        this.course = course;
        InitializeClassAttributes();
        GetAttendingCourseListByCourseId();
        GetCourseFeedbackListFromAttendingCourseList();
        CalculateAverageStarsFromCourseFeedbackList();
        return averageStars;
    }

    private void InitializeClassAttributes() {
        attendingCourseList = new ArrayList<>();
        courseFeedbackList = new ArrayList<>();
    }

    private void GetAttendingCourseListByCourseId() {
        attendingCourseList = attendingCourseRepository.findByCourse(course);
    }

    private void GetCourseFeedbackListFromAttendingCourseList() {
        for (AttendingCourse attendingCourse : attendingCourseList) {
            CourseFeedback courseFeedback = attendingCourse.getCourseFeedback();
            courseFeedbackList.add(courseFeedback);
        }
    }

    private void CalculateAverageStarsFromCourseFeedbackList() {
        int sumOfStars = 0;
        int starsCount = courseFeedbackList.size();
        for (CourseFeedback courseFeedback : courseFeedbackList) {
            int star = courseFeedback.getStar();
            sumOfStars += star;
        }
        if (sumOfStars == 0 || starsCount == 0) {
            averageStars = 0;
            return;
        }
        averageStars = (double) (sumOfStars) / starsCount;
    }
}

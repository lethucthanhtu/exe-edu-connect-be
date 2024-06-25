package com.theeduconnect.exeeduconnectbe.features.attendingCourse.services.impl;

import com.theeduconnect.exeeduconnectbe.domain.AttendingCourse;
import com.theeduconnect.exeeduconnectbe.domain.Course;
import com.theeduconnect.exeeduconnectbe.domain.CourseCategory;
import com.theeduconnect.exeeduconnectbe.features.attendingCourse.dtos.AttendingCourseDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendingCourseListToDtoListServiceImpl {
    private List<AttendingCourseDto> attendingCourseDtoList;
    private Course course;

    public AttendingCourseListToDtoListServiceImpl() {
        this.attendingCourseDtoList = new ArrayList<>();
    }

    public List<AttendingCourseDto> Handle(List<AttendingCourse> attendingCourseList) {
        for (AttendingCourse attendingCourse : attendingCourseList) {
            AttendingCourseDto attendingCourseDto =
                    MapAttendingCourseToAttendingCourseDto(attendingCourse);
            attendingCourseDtoList.add(attendingCourseDto);
        }
        return attendingCourseDtoList;
    }

    private AttendingCourseDto MapAttendingCourseToAttendingCourseDto(
            AttendingCourse attendingCourse) {
        AttendingCourseDto attendingCourseDto = new AttendingCourseDto();
        course = attendingCourse.getCourse();
        attendingCourseDto.setCoursecategoryname(GetCourseCategoryNameByCourse());
        attendingCourseDto.setCoursename(course.getName());
        attendingCourseDto.setStartdate(course.getStartdate());
        attendingCourseDto.setEnddate(course.getEnddate());
        attendingCourseDto.setIslearning(GetIsLearningStatus());
        return attendingCourseDto;
    }

    private String GetCourseCategoryNameByCourse() {
        CourseCategory courseCategory = course.getCoursecategory();
        return courseCategory.getCategoryname();
    }

    private boolean GetIsLearningStatus() {
        LocalDate startDate = course.getStartdate();
        LocalDate endDate = course.getEnddate();
        LocalDate today = LocalDate.now();
        if (today.isAfter(startDate) && today.isBefore(endDate)) return true;
        return false;
    }
}

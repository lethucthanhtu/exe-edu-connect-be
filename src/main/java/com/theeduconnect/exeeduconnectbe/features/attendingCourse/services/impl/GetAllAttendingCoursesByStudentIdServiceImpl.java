package com.theeduconnect.exeeduconnectbe.features.attendingCourse.services.impl;

import com.theeduconnect.exeeduconnectbe.constants.attendingCourse.AttendingCourseServiceHttpResponseCodes;
import com.theeduconnect.exeeduconnectbe.constants.attendingCourse.AttendingCourseServiceMessages;
import com.theeduconnect.exeeduconnectbe.domain.AttendingCourse;
import com.theeduconnect.exeeduconnectbe.domain.Student;
import com.theeduconnect.exeeduconnectbe.features.attendingCourse.dtos.AttendingCourseDto;
import com.theeduconnect.exeeduconnectbe.features.attendingCourse.payload.response.AttendingCourseServiceResponse;
import com.theeduconnect.exeeduconnectbe.repositories.AttendingCourseRepository;
import com.theeduconnect.exeeduconnectbe.repositories.StudentRepository;
import java.util.ArrayList;
import java.util.List;

public class GetAllAttendingCoursesByStudentIdServiceImpl {
    private final AttendingCourseRepository attendingCourseRepository;
    private final StudentRepository studentRepository;
    private AttendingCourseListToDtoListServiceImpl attendingCourseListToDtoListServiceImpl;
    private List<AttendingCourse> attendingCourseList;
    private List<AttendingCourseDto> attendingCourseDtoList;
    private int studentId;
    private Student student;

    public GetAllAttendingCoursesByStudentIdServiceImpl(
            AttendingCourseRepository attendingCourseRepository,
            StudentRepository studentRepository) {
        this.attendingCourseRepository = attendingCourseRepository;
        this.studentRepository = studentRepository;
        this.attendingCourseListToDtoListServiceImpl =
                new AttendingCourseListToDtoListServiceImpl();
    }

    public AttendingCourseServiceResponse Handle(int studentId) {
        try {
            this.studentId = studentId;
            ResetPreviousSearchResults();
            GetAttendingCoursesByStudent();
            if (!HasStudentAttendedAnyCourse()) return StudentHasNotAttendedAnyCourseResult();
            MapAttendingCourseListToAttendingCourseDtoList();
            return GetAllAttendingCoursesByStudentIdSuccessfulResult();
        } catch (Exception e) {
            return InternalServerErrorResult(e);
        }
    }

    private void ResetPreviousSearchResults() {
        attendingCourseList = new ArrayList<>();
        attendingCourseDtoList = new ArrayList<>();
        student = null;
    }

    private void GetAttendingCoursesByStudent() {
        student = studentRepository.findById(studentId).get();
        attendingCourseList = attendingCourseRepository.findAllByStudent(student);
    }

    private boolean HasStudentAttendedAnyCourse() {
        if (attendingCourseList.isEmpty()) return false;
        return true;
    }

    private void MapAttendingCourseListToAttendingCourseDtoList() {
        attendingCourseDtoList =
                attendingCourseListToDtoListServiceImpl.Handle(attendingCourseList);
    }

    private AttendingCourseServiceResponse StudentHasNotAttendedAnyCourseResult() {
        return new AttendingCourseServiceResponse(
                AttendingCourseServiceHttpResponseCodes.STUDENT_HAS_NOT_ATTENDED_COURSE,
                AttendingCourseServiceMessages.STUDENT_HAS_NOT_ATTENDED_COURSE,
                null);
    }

    private AttendingCourseServiceResponse GetAllAttendingCoursesByStudentIdSuccessfulResult() {
        return new AttendingCourseServiceResponse(
                AttendingCourseServiceHttpResponseCodes.GET_ALL_BY_STUDENT_ID_SUCCESSFUL,
                AttendingCourseServiceMessages.GET_ALL_BY_STUDENT_ID_SUCCESSFUL,
                attendingCourseDtoList);
    }

    private AttendingCourseServiceResponse InternalServerErrorResult(Exception e) {
        return new AttendingCourseServiceResponse(
                AttendingCourseServiceHttpResponseCodes.INTERNAL_SERVER_ERROR,
                AttendingCourseServiceMessages.INTERNAL_SERVER_ERROR,
                e.getMessage());
    }
}

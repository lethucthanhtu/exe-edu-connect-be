package com.theeduconnect.exeeduconnectbe.features.teacher.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.TeacherMapper;
import com.theeduconnect.exeeduconnectbe.constants.teacher.responseCodes.TeacherServiceHttpResponseCodes;
import com.theeduconnect.exeeduconnectbe.constants.teacher.serviceMessages.TeacherServiceMessages;
import com.theeduconnect.exeeduconnectbe.domain.Teacher;
import com.theeduconnect.exeeduconnectbe.domain.User;
import com.theeduconnect.exeeduconnectbe.features.teacher.dtos.TeacherDetailsDto;
import com.theeduconnect.exeeduconnectbe.features.teacher.payload.response.TeacherServiceResponse;
import com.theeduconnect.exeeduconnectbe.repositories.TeacherRepository;
import java.util.Optional;

public class GetByIdServiceImpl {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private int teacherId;
    private Teacher teacher;
    private User user;

    private TeacherDetailsDto teacherDetailsDto;

    public GetByIdServiceImpl(TeacherRepository teacherRepository, TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
    }

    public TeacherServiceResponse Handle(int id) {
        try {
            this.teacherId = id;
            ResetPreviousSearchResults();
            if (!IsTeacherAvailable()) return TeacherNotFoundResult();
            MapTeacherToTeacherDetailsDto();
            return GetTeacherByIdSuccessfulResult();
        } catch (Exception e) {
            return InternalServerErrorResult(e);
        }
    }

    private void ResetPreviousSearchResults() {
        teacher = null;
        user = null;
    }

    private void MapTeacherToTeacherDetailsDto() {
        teacherDetailsDto = teacherMapper.TeacherAndUserEntityToTeacherDetailsDto(teacher, user);
    }

    private boolean IsTeacherAvailable() {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);
        if (optionalTeacher.isEmpty()) return false;
        teacher = optionalTeacher.get();
        user = teacher.getUser();
        return true;
    }

    private TeacherServiceResponse GetTeacherByIdSuccessfulResult() {
        return new TeacherServiceResponse(
                TeacherServiceHttpResponseCodes.FOUND_TEACHER_BY_ID,
                TeacherServiceMessages.FOUND_TEACHER_BY_ID,
                teacherDetailsDto);
    }

    private TeacherServiceResponse TeacherNotFoundResult() {
        return new TeacherServiceResponse(
                TeacherServiceHttpResponseCodes.TEACHER_NOT_FOUND,
                TeacherServiceMessages.TEACHER_NOT_FOUND,
                null);
    }

    private TeacherServiceResponse InternalServerErrorResult(Exception e) {
        return new TeacherServiceResponse(
                TeacherServiceHttpResponseCodes.INTERNAL_SERVER_ERROR,
                TeacherServiceMessages.INTERNAL_SERVER_ERROR,
                e.getMessage());
    }
}

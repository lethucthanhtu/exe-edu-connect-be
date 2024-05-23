package com.theeduconnect.exeeduconnectbe.features.teacher.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.TeacherMapper;
import com.theeduconnect.exeeduconnectbe.features.teacher.payload.request.GetAllTeachersByRequest;
import com.theeduconnect.exeeduconnectbe.features.teacher.payload.response.TeacherServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.teacher.services.TeacherService;
import com.theeduconnect.exeeduconnectbe.repositories.CourseCategoryRepository;
import com.theeduconnect.exeeduconnectbe.repositories.TeacherRepository;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final CourseCategoryRepository courseCategoryRepository;
    private GetByCourseCategoryServiceImpl getByCourseCategoryServiceImpl;
    private GetByIdServiceImpl getByIdServiceImpl;
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    public TeacherServiceImpl(
            CourseCategoryRepository courseCategoryRepository,
            TeacherRepository teacherRepository,
            TeacherMapper teacherMapper) {
        this.courseCategoryRepository = courseCategoryRepository;
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
        InitializeChildServices();
    }

    @Override
    public TeacherServiceResponse getAllByRequest(GetAllTeachersByRequest request) {
        return getByCourseCategoryServiceImpl.Handle(request);
    }

    @Override
    public TeacherServiceResponse getById(int id) {
        return getByIdServiceImpl.Handle(id);
    }

    private void InitializeChildServices() {
        getByCourseCategoryServiceImpl =
                new GetByCourseCategoryServiceImpl(courseCategoryRepository);
        getByIdServiceImpl = new GetByIdServiceImpl(teacherRepository, teacherMapper);
    }
}

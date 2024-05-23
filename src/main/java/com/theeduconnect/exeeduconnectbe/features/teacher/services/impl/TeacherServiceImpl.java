package com.theeduconnect.exeeduconnectbe.features.teacher.services.impl;

import com.theeduconnect.exeeduconnectbe.features.teacher.payload.request.GetAllTeachersByRequest;
import com.theeduconnect.exeeduconnectbe.features.teacher.payload.response.TeacherServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.teacher.services.TeacherService;
import com.theeduconnect.exeeduconnectbe.repositories.CourseCategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final CourseCategoryRepository courseCategoryRepository;
    private GetByCourseCategoryServiceImpl getByCourseCategoryServiceImpl;

    public TeacherServiceImpl(CourseCategoryRepository courseCategoryRepository) {
        this.courseCategoryRepository = courseCategoryRepository;
        InitializeChildServices();
    }

    @Override
    public TeacherServiceResponse getAllByRequest(GetAllTeachersByRequest request) {
        return getByCourseCategoryServiceImpl.Handle(request);
    }

    private void InitializeChildServices() {
        getByCourseCategoryServiceImpl =
                new GetByCourseCategoryServiceImpl(courseCategoryRepository);
    }
}

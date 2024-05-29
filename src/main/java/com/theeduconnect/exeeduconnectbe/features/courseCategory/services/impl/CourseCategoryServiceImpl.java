package com.theeduconnect.exeeduconnectbe.features.courseCategory.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.CourseCategoryMapper;
import com.theeduconnect.exeeduconnectbe.features.courseCategory.payload.response.CourseCategoryServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.courseCategory.services.CourseCategoryService;
import com.theeduconnect.exeeduconnectbe.repositories.CourseCategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

    private final CourseCategoryMapper courseCategoryMapper;
    private final CourseCategoryRepository courseCategoryRepository;
    private GetAllCourseCategoryServiceImpl getAllCourseCategoryServiceImpl;
    private GetCourseCategoryByNameServiceImpl getCourseCategoryByNameServiceImpl;

    public CourseCategoryServiceImpl(
            CourseCategoryRepository courseCategoryRepository,
            CourseCategoryMapper courseCategoryMapper) {
        this.courseCategoryRepository = courseCategoryRepository;
        this.courseCategoryMapper = courseCategoryMapper;
        InitializeChildServices();
    }

    @Override
    public CourseCategoryServiceResponse getAll() {
        return getAllCourseCategoryServiceImpl.Handle();
    }

    @Override
    public CourseCategoryServiceResponse getByName(String categoryName) {
        return getCourseCategoryByNameServiceImpl.Handle(categoryName);
    }

    private void InitializeChildServices() {
        getAllCourseCategoryServiceImpl =
                new GetAllCourseCategoryServiceImpl(courseCategoryRepository, courseCategoryMapper);
        getCourseCategoryByNameServiceImpl = new GetCourseCategoryByNameServiceImpl(
                courseCategoryRepository,courseCategoryMapper
        );
    }
}

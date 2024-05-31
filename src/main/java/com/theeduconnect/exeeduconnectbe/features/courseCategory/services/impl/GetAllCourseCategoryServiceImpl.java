package com.theeduconnect.exeeduconnectbe.features.courseCategory.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.CourseCategoryMapper;
import com.theeduconnect.exeeduconnectbe.constants.courseCategory.CourseCategoryServiceHttpResponseCodes;
import com.theeduconnect.exeeduconnectbe.constants.courseCategory.CourseCategoryServiceMessages;
import com.theeduconnect.exeeduconnectbe.domain.CourseCategory;
import com.theeduconnect.exeeduconnectbe.features.courseCategory.dtos.CourseCategoryDto;
import com.theeduconnect.exeeduconnectbe.features.courseCategory.payload.response.CourseCategoryServiceResponse;
import com.theeduconnect.exeeduconnectbe.repositories.CourseCategoryRepository;
import java.util.ArrayList;
import java.util.List;

public class GetAllCourseCategoryServiceImpl {
    private final CourseCategoryRepository courseCategoryRepository;
    private final CourseCategoryMapper courseCategoryMapper;
    private List<CourseCategory> courseCategoryList;
    private List<CourseCategoryDto> courseCategoryDtoList;

    public GetAllCourseCategoryServiceImpl(
            CourseCategoryRepository courseCategoryRepository,
            CourseCategoryMapper courseCategoryMapper) {
        this.courseCategoryRepository = courseCategoryRepository;
        this.courseCategoryMapper = courseCategoryMapper;
    }

    public CourseCategoryServiceResponse Handle() {
        try {
            ResetPreviousSearchResults();
            GetAllCourseCategoriesFromDatabase();
            MapCourseCategoryListToCourseCategoryDtoList();
            return GetAllCourseCategoriesSuccessful();
        } catch (Exception e) {
            return InternalServerErrorResult(e);
        }
    }

    private void ResetPreviousSearchResults() {
        courseCategoryList = new ArrayList<>();
        courseCategoryDtoList = new ArrayList<>();
    }

    private void GetAllCourseCategoriesFromDatabase() {
        courseCategoryList = courseCategoryRepository.findAll();
    }

    private void MapCourseCategoryListToCourseCategoryDtoList() {
        for (CourseCategory courseCategory : courseCategoryList) {
            CourseCategoryDto courseCategoryDto =
                    courseCategoryMapper.CourseCategoryEntityToCourseCategoryDto(courseCategory);
            courseCategoryDto.setTotal(courseCategory.getCourses().size());
            courseCategoryDtoList.add(courseCategoryDto);
        }
    }

    private CourseCategoryServiceResponse GetAllCourseCategoriesSuccessful() {
        return new CourseCategoryServiceResponse(
                CourseCategoryServiceHttpResponseCodes.GET_ALL_COURSE_CATEGORIES_SUCCESSFUL,
                CourseCategoryServiceMessages.GET_ALL_COURSE_CATEGORIES_SUCCESSFUL,
                courseCategoryDtoList);
    }

    private CourseCategoryServiceResponse InternalServerErrorResult(Exception e) {
        return new CourseCategoryServiceResponse(
                CourseCategoryServiceHttpResponseCodes.INTERNAL_SERVER_ERROR,
                CourseCategoryServiceMessages.INTERNAL_SERVER_ERROR,
                e.getMessage());
    }
}

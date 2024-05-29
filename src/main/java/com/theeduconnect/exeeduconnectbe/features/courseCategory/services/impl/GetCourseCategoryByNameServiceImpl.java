package com.theeduconnect.exeeduconnectbe.features.courseCategory.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.CourseCategoryMapper;
import com.theeduconnect.exeeduconnectbe.constants.courseCategory.responseCodes.CourseCategoryServiceHttpResponseCodes;
import com.theeduconnect.exeeduconnectbe.constants.courseCategory.serviceMessages.CourseCategoryServiceMessages;
import com.theeduconnect.exeeduconnectbe.domain.CourseCategory;
import com.theeduconnect.exeeduconnectbe.features.courseCategory.dtos.CourseCategoryDto;
import com.theeduconnect.exeeduconnectbe.features.courseCategory.payload.response.CourseCategoryServiceResponse;
import com.theeduconnect.exeeduconnectbe.repositories.CourseCategoryRepository;

import java.util.Optional;

public class GetCourseCategoryByNameServiceImpl {

    private final CourseCategoryRepository courseCategoryRepository;
    private final CourseCategoryMapper courseCategoryMapper;
    private String courseCategoryName;
    private CourseCategory courseCategory;
    private CourseCategoryDto courseCategoryDto;

    public GetCourseCategoryByNameServiceImpl(CourseCategoryRepository courseCategoryRepository,
                                              CourseCategoryMapper courseCategoryMapper){
        this.courseCategoryRepository = courseCategoryRepository;
        this.courseCategoryMapper = courseCategoryMapper;
    }
    public CourseCategoryServiceResponse Handle(String courseCategoryName){
        try {
            this.courseCategoryName = courseCategoryName;
            if (!IsCourseCategoryFound()) return CourseCategoryNameNotFoundResult();
            MapCourseCategoryEntityToCourseCategoryDto();
            return GetCourseCategoryByNameSuccessfulResult();
        }catch (Exception e){
            return InternalServerErrorResult(e);
        }
    }
    private boolean IsCourseCategoryFound(){
        courseCategory = courseCategoryRepository.findByCategoryname(courseCategoryName);
        if(courseCategory==null) return false;
        return true;
    }
    private void MapCourseCategoryEntityToCourseCategoryDto(){
        courseCategoryDto = courseCategoryMapper.CourseCategoryEntityToCourseCategoryDto(courseCategory);
        courseCategoryDto.setTotal(courseCategory.getCourses().size());
    }
    private CourseCategoryServiceResponse GetCourseCategoryByNameSuccessfulResult() {
        return new CourseCategoryServiceResponse(
                CourseCategoryServiceHttpResponseCodes.GET_COURSE_CATEGORY_BY_NAME_SUCCESSFUL,
                CourseCategoryServiceMessages.GET_COURSE_CATEGORY_BY_NAME_SUCCESSFUL,
                courseCategoryDto);
    }
    private CourseCategoryServiceResponse  CourseCategoryNameNotFoundResult(){
        return new CourseCategoryServiceResponse(
                CourseCategoryServiceHttpResponseCodes.COURSE_CATEGORY_NOT_FOUND,
                CourseCategoryServiceMessages.COURSE_CATEGORY_NOT_FOUND,
                null);
    }

    private CourseCategoryServiceResponse InternalServerErrorResult(Exception e) {
        return new CourseCategoryServiceResponse(
                CourseCategoryServiceHttpResponseCodes.INTERNAL_SERVER_ERROR,
                CourseCategoryServiceMessages.INTERNAL_SERVER_ERROR,
                e.getMessage());
    }
}


package com.theeduconnect.exeeduconnectbe.features.courseCategory.services;

import com.theeduconnect.exeeduconnectbe.features.courseCategory.payload.response.CourseCategoryServiceResponse;

public interface CourseCategoryService {
    CourseCategoryServiceResponse getAll();
    CourseCategoryServiceResponse getByName(String categoryName);
}

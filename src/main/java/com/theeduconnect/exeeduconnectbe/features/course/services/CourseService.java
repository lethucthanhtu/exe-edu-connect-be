package com.theeduconnect.exeeduconnectbe.features.course.services;

import com.theeduconnect.exeeduconnectbe.features.course.payload.request.NewCourseRequest;
import com.theeduconnect.exeeduconnectbe.features.course.payload.request.PaginationRequest;
import com.theeduconnect.exeeduconnectbe.features.course.payload.response.CourseServiceResponse;

public interface CourseService {
    CourseServiceResponse getAllWithPagination(PaginationRequest pageRequest);

    CourseServiceResponse create(NewCourseRequest request);
}

package com.theeduconnect.exeeduconnectbe.features.course.services;

import com.theeduconnect.exeeduconnectbe.features.course.payload.request.GetAllCoursesByRequest;
import com.theeduconnect.exeeduconnectbe.features.course.payload.request.NewCourseRequest;
import com.theeduconnect.exeeduconnectbe.features.course.payload.response.CourseServiceResponse;

public interface CourseService {
    CourseServiceResponse getAllByRequest(GetAllCoursesByRequest request);

    CourseServiceResponse create(NewCourseRequest request);
}

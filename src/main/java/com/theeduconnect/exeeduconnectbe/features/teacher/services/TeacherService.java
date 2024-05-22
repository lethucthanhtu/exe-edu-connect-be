package com.theeduconnect.exeeduconnectbe.features.teacher.services;

import com.theeduconnect.exeeduconnectbe.features.teacher.payload.request.GetAllTeachersByRequest;
import com.theeduconnect.exeeduconnectbe.features.teacher.payload.response.TeacherServiceResponse;

public interface TeacherService {
    TeacherServiceResponse getAllByRequest(GetAllTeachersByRequest request);
}

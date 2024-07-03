package com.theeduconnect.exeeduconnectbe.features.teacherProfile.services;

import com.theeduconnect.exeeduconnectbe.features.teacherProfile.dtos.UpdateTeacherProfileDto;
import com.theeduconnect.exeeduconnectbe.features.teacherProfile.payload.response.TeacherProfileServiceResponse;

public interface TeacherProfileService {
    TeacherProfileServiceResponse update(UpdateTeacherProfileDto request);
}

package com.theeduconnect.exeeduconnectbe.features.course.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinCourseRequest {
    @JsonIgnore
    private int studentId;
}

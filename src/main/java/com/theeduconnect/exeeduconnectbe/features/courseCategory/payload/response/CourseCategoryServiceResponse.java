package com.theeduconnect.exeeduconnectbe.features.courseCategory.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CourseCategoryServiceResponse {
    private int statusCode;
    private String message;
    private Object returnData;
}

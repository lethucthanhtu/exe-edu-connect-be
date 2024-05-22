package com.theeduconnect.exeeduconnectbe.features.teacher.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetAllTeachersByRequest {
    private String categoryname;
    private int page;
    private int size;
}

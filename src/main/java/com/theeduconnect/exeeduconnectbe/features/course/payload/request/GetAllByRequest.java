package com.theeduconnect.exeeduconnectbe.features.course.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetAllByRequest {
    private String categoryname;
    private int page;
    private int size;
}
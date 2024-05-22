package com.theeduconnect.exeeduconnectbe.features.course.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetByCategoryAndPaginationRequest {
    private String categoryname;
    private int page;
    private int size;
}

package com.theeduconnect.exeeduconnectbe.features.course.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaginationRequest {
    private int page;
    private int size;
}

package com.theeduconnect.exeeduconnectbe.configs.errorHandling;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorModel {

    private String code;
    private String detail;
    private String source;
}

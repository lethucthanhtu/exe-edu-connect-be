package com.theeduconnect.exeeduconnectbe.configs.errorHandling;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseModel {

    public String type;

    public List<ErrorModel> errorModels;
}

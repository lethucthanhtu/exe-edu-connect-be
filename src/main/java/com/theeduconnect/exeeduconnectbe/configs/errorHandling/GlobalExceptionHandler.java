package com.theeduconnect.exeeduconnectbe.configs.errorHandling;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
@Configuration
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponseModel handleException(MethodArgumentNotValidException e) {
        List<ErrorModel> errorModels = processErrors(e);
        return ErrorResponseModel.builder().errorModels(errorModels).type("Validation").build();
    }

    private List<ErrorModel> processErrors(MethodArgumentNotValidException e) {
        List<ErrorModel> validationErrorModels = new ArrayList<ErrorModel>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            ErrorModel validationErrorModel =
                    ErrorModel.builder()
                            .code(fieldError.getCode())
                            .source(fieldError.getObjectName() + "/" + fieldError.getField())
                            .detail(fieldError.getField() + " " + fieldError.getDefaultMessage())
                            .build();
            validationErrorModels.add(validationErrorModel);
        }
        return validationErrorModels;
    }
}

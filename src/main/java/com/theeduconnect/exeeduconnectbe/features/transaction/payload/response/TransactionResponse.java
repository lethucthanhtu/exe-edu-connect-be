package com.theeduconnect.exeeduconnectbe.features.transaction.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransactionResponse {
    private int statusCode;
    private String message;
    private Object returnData;
}

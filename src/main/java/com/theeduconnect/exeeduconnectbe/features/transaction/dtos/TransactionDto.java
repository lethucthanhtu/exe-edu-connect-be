package com.theeduconnect.exeeduconnectbe.features.transaction.dtos;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto {
    private Integer id;
    private Integer courseid;
    private Integer userid;
    private Double price;
    private LocalDate datetime;
    private Integer transactioncategoryid;
    private String status;
}

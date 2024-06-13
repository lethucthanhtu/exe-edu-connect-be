package com.theeduconnect.exeeduconnectbe.features.transaction.dtos;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class TransactionDto {
    private Integer id;
    private Integer courseid;
    private Integer userid;
    private Double price;
    private LocalDate datetime;
    private Integer transactioncategoryid;
}

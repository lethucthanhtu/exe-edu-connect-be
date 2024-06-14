package com.theeduconnect.exeeduconnectbe.features.transaction.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class TransactionRequest {
    @Schema(name = "courseid", example = "1")
    private Integer courseid;

    @Schema(name = "userid", example = "1")
    @NotNull(message = "userid is required")
    private Integer userid;

    @Schema(name = "price", example = "100.0")
    private Double price;

    @Schema(name = "datetime", example = "2024-06-14")
    private LocalDate datetime;

    @Schema(name = "transactioncategoryid", example = "1")
    @NotNull(message = "transactioncategoryid is required")
    private Integer transactioncategoryid;
}

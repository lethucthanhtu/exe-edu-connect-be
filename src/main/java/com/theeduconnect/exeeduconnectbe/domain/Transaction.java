package com.theeduconnect.exeeduconnectbe.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transactionid", nullable = false)
    private Integer id;

    @Column(name = "courseid")
    private Integer courseid;

    @NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userid", nullable = false)
    @JsonManagedReference
    private User user;

    @Column(name = "price")
    private Double price;

    @Column(name = "datetime")
    private LocalDate datetime;

    @NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transactioncategoryid", nullable = false)
    private TransactionCategory transactioncategory;

    @Column(name = "status")
    private String status;
}

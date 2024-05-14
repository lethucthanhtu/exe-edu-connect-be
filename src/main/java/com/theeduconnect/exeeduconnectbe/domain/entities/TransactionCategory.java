package com.theeduconnect.exeeduconnectbe.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "transaction_categories")
public class TransactionCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transactioncategoryid", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "categoryname", length = 50)
    private String categoryname;

    @OneToMany(mappedBy = "transactioncategory")
    private Set<Transaction> transactions = new LinkedHashSet<>();

}
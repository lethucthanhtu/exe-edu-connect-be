package com.theeduconnect.exeeduconnectbe.features.transaction.controllers;

import com.theeduconnect.exeeduconnectbe.constants.transaction.TransactionEndpoints;
import com.theeduconnect.exeeduconnectbe.features.transaction.payload.request.TransactionRequest;
import com.theeduconnect.exeeduconnectbe.features.transaction.payload.response.TransactionResponse;
import com.theeduconnect.exeeduconnectbe.features.transaction.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(TransactionEndpoints.BASE)
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    @Operation(summary = "Get all transactions")
    public ResponseEntity<TransactionResponse> getAllTransactions() {
        TransactionResponse response = transactionService.getAllTransactions();
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping(TransactionEndpoints.GET_BY_ID)
    @Operation(summary = "Get a transaction by Id")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable int id) {
        TransactionResponse response = transactionService.getTransactionById(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PostMapping
    @Operation(summary = "Create a new transaction")
    public ResponseEntity<TransactionResponse> createTransaction(
            @Valid @RequestBody TransactionRequest request) {
        TransactionResponse response = transactionService.createTransaction(request);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PutMapping(TransactionEndpoints.UPDATE)
    @Operation(summary = "Update a transaction by Id")
    public ResponseEntity<TransactionResponse> updateTransaction(
            @PathVariable int id, @Valid @RequestBody TransactionRequest request) {
        TransactionResponse response = transactionService.updateTransaction(id, request);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @DeleteMapping(TransactionEndpoints.DELETE)
    @Operation(summary = "Delete a transaction by Id")
    public ResponseEntity<TransactionResponse> deleteTransaction(@PathVariable int id) {
        TransactionResponse response = transactionService.deleteTransaction(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }
}

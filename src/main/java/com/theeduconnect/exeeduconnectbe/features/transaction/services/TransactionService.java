package com.theeduconnect.exeeduconnectbe.features.transaction.services;

import com.theeduconnect.exeeduconnectbe.features.transaction.payload.request.TransactionRequest;
import com.theeduconnect.exeeduconnectbe.features.transaction.payload.response.TransactionResponse;

public interface TransactionService {
    TransactionResponse getAllTransactions();

    TransactionResponse getTransactionById(int id);

    TransactionResponse createTransaction(TransactionRequest request);

    TransactionResponse updateTransaction(int id, TransactionRequest request);

    TransactionResponse deleteTransaction(int id);
}

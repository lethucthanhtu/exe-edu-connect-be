package com.theeduconnect.exeeduconnectbe.features.transaction.services.impl;

import com.theeduconnect.exeeduconnectbe.constants.transaction.TransactionMessages;
import com.theeduconnect.exeeduconnectbe.domain.*;
import com.theeduconnect.exeeduconnectbe.features.transaction.dtos.TransactionDto;
import com.theeduconnect.exeeduconnectbe.features.transaction.payload.request.TransactionRequest;
import com.theeduconnect.exeeduconnectbe.features.transaction.payload.response.TransactionResponse;
import com.theeduconnect.exeeduconnectbe.features.transaction.services.TransactionService;
import com.theeduconnect.exeeduconnectbe.repositories.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired private TransactionRepository transactionRepository;

    @Autowired private UserRepository userRepository;

    @Autowired private TransactionCategoryRepository transactionCategoryRepository;

    @Autowired private CourseRepository courseRepository;

    @Override
    public TransactionResponse getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        List<TransactionDto> dtos =
                transactions.stream().map(this::convertToDto).collect(Collectors.toList());
        return new TransactionResponse(
                HttpStatus.OK.value(), TransactionMessages.ALL_TRANSACTIONS_FOUND, dtos);
    }

    @Override
    public TransactionResponse getTransactionById(int id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isPresent()) {
            return new TransactionResponse(
                    HttpStatus.OK.value(),
                    TransactionMessages.TRANSACTION_FOUND,
                    convertToDto(transaction.get()));
        } else {
            return new TransactionResponse(
                    HttpStatus.NOT_FOUND.value(), TransactionMessages.TRANSACTION_NOT_FOUND, null);
        }
    }

    @Override
    public TransactionResponse createTransaction(TransactionRequest request) {
        Transaction transaction = new Transaction();
        transaction.setDatetime(request.getDatetime());

        if (request.getPrice() <= 0) {
            return new TransactionResponse(
                    HttpStatus.BAD_REQUEST.value(), TransactionMessages.PRICE_GREATER_THAN_0, null);
        } else {
            transaction.setPrice(request.getPrice());
        }

        Optional<User> user = userRepository.findById(request.getUserid());
        if (user.isPresent()) {
            transaction.setUser(user.get());
        } else {
            return new TransactionResponse(
                    HttpStatus.NOT_FOUND.value(), TransactionMessages.USER_NOT_FOUND, null);
        }

        Optional<TransactionCategory> category =
                transactionCategoryRepository.findById(request.getTransactioncategoryid());
        if (category.isPresent()) {
            transaction.setTransactioncategory(category.get());
        } else {
            return new TransactionResponse(
                    HttpStatus.NOT_FOUND.value(),
                    TransactionMessages.TRANSACTION_CATEGORY_NOT_FOUND,
                    null);
        }

        Optional<Course> course = courseRepository.findById(request.getCourseid());
        if (course.isPresent()) {
            transaction.setCourseid(course.get().getId());
        } else {
            return new TransactionResponse(
                    HttpStatus.NOT_FOUND.value(), TransactionMessages.COURSE_NOT_FOUND, null);
        }

        transaction.setStatus(request.getStatus());
        transactionRepository.save(transaction);
        return new TransactionResponse(
                HttpStatus.CREATED.value(),
                TransactionMessages.TRANSACTION_CREATED,
                convertToDto(transaction));
    }

    @Override
    public TransactionResponse updateTransaction(int id, TransactionRequest request) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (optionalTransaction.isPresent()) {
            Transaction transaction = optionalTransaction.get();
            transaction.setDatetime(request.getDatetime());

            if (request.getPrice() <= 0) {
                return new TransactionResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        TransactionMessages.PRICE_GREATER_THAN_0,
                        null);
            } else {
                transaction.setPrice(request.getPrice());
            }

            Optional<User> user = userRepository.findById(request.getUserid());
            if (user.isPresent()) {
                transaction.setUser(user.get());
            } else {
                return new TransactionResponse(
                        HttpStatus.NOT_FOUND.value(), TransactionMessages.USER_NOT_FOUND, null);
            }

            Optional<TransactionCategory> category =
                    transactionCategoryRepository.findById(request.getTransactioncategoryid());
            if (category.isPresent()) {
                transaction.setTransactioncategory(category.get());
            } else {
                return new TransactionResponse(
                        HttpStatus.NOT_FOUND.value(),
                        TransactionMessages.TRANSACTION_CATEGORY_NOT_FOUND,
                        null);
            }

            Optional<Course> course = courseRepository.findById(request.getCourseid());
            if (course.isPresent()) {
                transaction.setCourseid(course.get().getId());
            } else {
                return new TransactionResponse(
                        HttpStatus.NOT_FOUND.value(), TransactionMessages.COURSE_NOT_FOUND, null);
            }

            transaction.setStatus(request.getStatus());
            transactionRepository.save(transaction);
            return new TransactionResponse(
                    HttpStatus.OK.value(),
                    TransactionMessages.TRANSACTION_UPDATED,
                    convertToDto(transaction));
        } else {
            return new TransactionResponse(
                    HttpStatus.NOT_FOUND.value(), TransactionMessages.TRANSACTION_NOT_FOUND, null);
        }
    }

    @Override
    public TransactionResponse deleteTransaction(int id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isPresent()) {
            transactionRepository.delete(transaction.get());
            return new TransactionResponse(
                    HttpStatus.OK.value(), TransactionMessages.TRANSACTION_DELETED, null);
        } else {
            return new TransactionResponse(
                    HttpStatus.NOT_FOUND.value(), TransactionMessages.TRANSACTION_NOT_FOUND, null);
        }
    }

    @Override
    public TransactionResponse getTransactionsByUserId(int userid) {
        // Check if user exists
        Optional<User> userOptional = userRepository.findById(userid);
        if (!userOptional.isPresent()) {
            return new TransactionResponse(
                    HttpStatus.NOT_FOUND.value(), TransactionMessages.USER_NOT_FOUND, null);
        }

        List<Transaction> transactions = transactionRepository.findByUserid_Id(userid);
        if (!transactions.isEmpty()) {
            List<TransactionDto> dtos =
                    transactions.stream().map(this::convertToDto).collect(Collectors.toList());
            return new TransactionResponse(
                    HttpStatus.OK.value(), TransactionMessages.TRANSACTION_FOUND, dtos);
        } else {
            return new TransactionResponse(
                    HttpStatus.NOT_FOUND.value(), TransactionMessages.TRANSACTION_NOT_FOUND, null);
        }
    }

    @Override
    public TransactionResponse updateTransactionStatus(int id, String status) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (optionalTransaction.isPresent()) {
            Transaction transaction = optionalTransaction.get();
            transaction.setStatus(status); // Cập nhật trạng thái
            transactionRepository.save(transaction);
            return new TransactionResponse(
                    HttpStatus.OK.value(),
                    TransactionMessages.TRANSACTION_STATUS_UPDATED,
                    convertToDto(transaction));
        } else {
            return new TransactionResponse(
                    HttpStatus.NOT_FOUND.value(), TransactionMessages.TRANSACTION_NOT_FOUND, null);
        }
    }

    private TransactionDto convertToDto(Transaction transaction) {
        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId());
        dto.setCourseid(transaction.getCourseid());
        dto.setUserid(transaction.getUser().getId());
        dto.setPrice(transaction.getPrice());
        dto.setDatetime(transaction.getDatetime());
        dto.setTransactioncategoryid(transaction.getTransactioncategory().getId());
        dto.setStatus(transaction.getStatus());
        return dto;
    }
}

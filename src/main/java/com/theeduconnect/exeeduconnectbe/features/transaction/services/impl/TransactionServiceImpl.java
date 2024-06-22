package com.theeduconnect.exeeduconnectbe.features.transaction.services.impl;

import com.theeduconnect.exeeduconnectbe.constants.transaction.TransactionMessages;
import com.theeduconnect.exeeduconnectbe.domain.Course;
import com.theeduconnect.exeeduconnectbe.domain.Transaction;
import com.theeduconnect.exeeduconnectbe.domain.TransactionCategory;
import com.theeduconnect.exeeduconnectbe.domain.User;
import com.theeduconnect.exeeduconnectbe.features.transaction.dtos.TransactionDto;
import com.theeduconnect.exeeduconnectbe.features.transaction.payload.request.TransactionRequest;
import com.theeduconnect.exeeduconnectbe.features.transaction.payload.response.TransactionResponse;
import com.theeduconnect.exeeduconnectbe.features.transaction.services.TransactionService;
import com.theeduconnect.exeeduconnectbe.repositories.CourseRepository;
import com.theeduconnect.exeeduconnectbe.repositories.TransactionCategoryRepository;
import com.theeduconnect.exeeduconnectbe.repositories.TransactionRepository;
import com.theeduconnect.exeeduconnectbe.repositories.UserRepository;
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
            transaction.setUserid(user.get());
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
                transaction.setUserid(user.get());
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

    private TransactionDto convertToDto(Transaction transaction) {
        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId());
        dto.setCourseid(transaction.getCourseid());
        dto.setUserid(transaction.getUserid().getId());
        dto.setPrice(transaction.getPrice());
        dto.setDatetime(transaction.getDatetime());
        dto.setTransactioncategoryid(transaction.getTransactioncategory().getId());
        return dto;
    }
}
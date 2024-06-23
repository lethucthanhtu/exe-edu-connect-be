package com.theeduconnect.exeeduconnectbe.repositories;

import com.theeduconnect.exeeduconnectbe.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByUserid_Id(Integer userid);
}

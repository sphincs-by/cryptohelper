package com.sphincs.cryptohelper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sphincs.cryptohelper.domain.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByCurrency(String currency);

}

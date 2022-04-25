package com.sphincs.cryptohelper.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.sphincs.cryptohelper.domain.Transaction;
import com.sphincs.cryptohelper.repository.TransactionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionService {

    private TransactionRepository transactionRepository;

    public List<Transaction> getTransactions(final String currency) {
        return Objects.nonNull(currency) ? transactionRepository.findAllByCurrency(currency)
                : transactionRepository.findAll();
    }

    public Transaction addTransaction(final Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}

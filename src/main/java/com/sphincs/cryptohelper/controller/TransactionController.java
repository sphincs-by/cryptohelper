package com.sphincs.cryptohelper.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sphincs.cryptohelper.domain.Transaction;
import com.sphincs.cryptohelper.service.TransactionService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/transactions")
@AllArgsConstructor
public class TransactionController {

    private TransactionService transactionService;

    @GetMapping
    public List<Transaction> getTransactions(@RequestParam(required = false) String currency) {
        log.debug("IN TransactionController - getTransactions with currency [{}]", currency);

        List<Transaction> transactions = transactionService.getTransactions(currency);

        log.debug("OUT TransactionController - getTransactions with currency [{}]", currency);

        return transactions;
    }

    @PostMapping
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) {
        log.debug("IN TransactionController - addTransaction: [{}]", transaction);

        Transaction savedTransaction = transactionService.addTransaction(transaction);

        log.debug("OUT TransactionController - addTransaction: [{}]", savedTransaction);

        return ResponseEntity.ok(savedTransaction);
    }

}

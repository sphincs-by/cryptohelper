package com.sphincs.cryptohelper.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import com.sphincs.cryptohelper.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sphincs.cryptohelper.domain.Transaction;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    private static final long ID = 1L;
    private static final String CURRENCY = "BTC";
    private static final Transaction TRANSACTION = Transaction.builder()
            .id(ID)
            .currency(CURRENCY)
            .build();

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private TransactionService service;

    @Test
    public void getAllTransactionsTest() {
        final List<Transaction> expected = List.of(TRANSACTION);
        when(repository.findAll()).thenReturn(expected);

        final List<Transaction> actual = service.getTransactions(null);

        assertThat(actual, is(expected));
    }

    @Test
    public void getTransactionsByCurrencyTest() {
        final List<Transaction> expected = List.of(TRANSACTION);
        when(repository.findAllByCurrency(CURRENCY)).thenReturn(expected);

        final List<Transaction> actual = service.getTransactions(CURRENCY);

        assertThat(actual, is(expected));
    }

}

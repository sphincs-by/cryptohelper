package com.sphincs.cryptohelper.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import com.sphincs.cryptohelper.CryptohelperApplication;
import com.sphincs.cryptohelper.domain.Transaction;
import com.sphincs.cryptohelper.repository.TransactionRepository;

@SpringBootTest(classes = CryptohelperApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test-application.properties")
public class TransactionsTest {

    private static final Transaction initialTransaction = Transaction.builder()
            .currency("BTC")
            .build();
    private static final String ROOT_PATH = "http://localhost:%d/api/transactions";
    private static final ParameterizedTypeReference<List<Transaction>> transactionsType =
            new ParameterizedTypeReference<>() {
            };
    private static final RestTemplate restTemplate = getRestTemplate();

    @LocalServerPort
    protected int port;

    @Autowired
    private TransactionRepository repository;

    private static RestTemplate getRestTemplate() {
        final RestTemplate restTemplate = new RestTemplate();
        final SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }

    @BeforeEach
    public void setUp() {
        repository.save(initialTransaction);
    }

    @AfterEach
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    void saveTransactionTest() {
        Transaction transaction = Transaction.builder()
                .currency("ETH")
                .build();

        ResponseEntity<Transaction> actualResponseEntity =
                restTemplate.exchange(getRootUrl(), HttpMethod.POST, new HttpEntity<>(transaction), Transaction.class);

        Transaction actualTransaction = actualResponseEntity.getBody();

        assertThat(actualResponseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(actualTransaction, notNullValue());
        assertThat(actualTransaction.getId(), notNullValue());
        assertThat(actualTransaction.getCurrency(), is("ETH"));
    }

    @Test
    void getAllTransactionsTest() {
        Transaction transaction = Transaction.builder()
                .currency("ETH")
                .build();
        repository.save(transaction);

        ResponseEntity<List<Transaction>> actualResponseEntity =
                restTemplate.exchange(getRootUrl(), HttpMethod.GET, HttpEntity.EMPTY, transactionsType);

        List<Transaction> actualTransactions = actualResponseEntity.getBody();

        assertThat(actualResponseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(actualTransactions, notNullValue());
        assertThat(actualTransactions.size(), is(2));
        assertThat(actualTransactions.stream()
                .map(Transaction::getCurrency)
                .collect(Collectors.toList()), is(List.of("BTC", "ETH")));
    }

    @Test
    void getTransactionsByCurrencyTest() {
        ResponseEntity<List<Transaction>> actualResponseEntity =
                restTemplate.exchange(getRootUrl(), HttpMethod.GET, HttpEntity.EMPTY, transactionsType, "BTC");

        List<Transaction> actualTransactions = actualResponseEntity.getBody();

        assertThat(actualResponseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(actualTransactions, notNullValue());
        assertThat(actualTransactions.size(), is(1));
        assertThat(actualTransactions.get(0)
                .getCurrency(), is("BTC"));
    }

    private String getRootUrl() {
        return String.format(ROOT_PATH, port);
    }
}

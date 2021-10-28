package com.gemini.jobcoin.services.client;

import com.gemini.jobcoin.services.models.History;
import com.gemini.jobcoin.services.models.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class JobcoinHttpClientTest {

    private final String JOBCOIN_URL = "https://jobcoin.gemini.com/satin-badass";
    private final String ADDRESSES_ENDPOINT = "/api/addresses/";
    private final String TRANSACTIONS_ENDPOINT = "/api/transactions";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private JobcoinHttpClient jobcoinHttpClient;


    @Test
    public void getAddressHistory() throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction());

        History history = new History();
        history.setBalance("50");
        history.setTransactions(transactions);
        ResponseEntity<History> response = new ResponseEntity<>(history, HttpStatus.OK);

        Mockito.when(restTemplate
                .exchange(
                        JOBCOIN_URL + ADDRESSES_ENDPOINT + "address12345",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<History>() {}
                )).thenReturn(response);

        History hist = jobcoinHttpClient.getAddressHistory("address12345");
        Assertions.assertEquals(history, hist);
    }

    @Test
    public void getAllTransactions() throws Exception {
        Transaction transaction1 = new Transaction();
        transaction1.setTimestamp("12345");
        transaction1.setFromAddress("abc");
        transaction1.setToAddress("123");
        transaction1.setAmount("10");
        Transaction transaction2 = new Transaction();
        transaction1.setTimestamp("6789");
        transaction1.setFromAddress("xzy");
        transaction1.setToAddress("456");
        transaction1.setAmount("15");

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        Mockito.when(restTemplate
                .exchange(
                        JOBCOIN_URL + TRANSACTIONS_ENDPOINT,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Transaction>>() {}
                )).thenReturn(new ResponseEntity<>(transactions, HttpStatus.OK));

        List<Transaction> trans = jobcoinHttpClient.getAllTransactions();
        Assertions.assertEquals(transactions, trans);
    }

    @Test
    public void testSendCoins() {
        // TODO: Mockito test JobcoinHttpClient.sendCoins()
    }

}
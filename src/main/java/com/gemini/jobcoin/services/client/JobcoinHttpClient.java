package com.gemini.jobcoin.services.client;

import com.gemini.jobcoin.services.models.History;
import com.gemini.jobcoin.services.models.Transaction;
import com.gemini.jobcoin.services.models.TransferResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import java.util.List;

// HTTPClient should handle the interaction with the jobcoin rest api
@EnableAsync
public class JobcoinHttpClient {

    private final String JOBCOIN_URL = "https://jobcoin.gemini.com/satin-badass";
    private final String ADDRESSES_ENDPOINT = "/api/addresses/";
    private final String TRANSACTIONS_ENDPOINT = "/api/transactions";

    private final RestTemplate restTemplate;

    public JobcoinHttpClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public History getAddressHistory(String address) throws Exception {
        try {
            String url = buildAddressHistoryURL(address);

            ResponseEntity<History> addressHistory =
                    restTemplate.exchange(url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<History>(){}
                    );

            return addressHistory.getBody();
        } catch (Exception ex) {
            throw new Exception("Failed to get address history.");
        }
    }

    public List<Transaction> getAllTransactions() throws Exception{
        try {
            String url = buildTransactionsUrl();

            ResponseEntity<List<Transaction>> transactions =
                    restTemplate.exchange(url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Transaction>>() {
                    });

            return transactions.getBody();
        } catch (Exception ex) {
            throw new Exception("Failed to get transactions.");
        }
    }

    @Async
    public void sendCoins(String addressSource, String addressDestination, String amount) throws Exception{
        try {
            String url = buildTransactionsUrl();

            Transaction transaction = new Transaction(null, addressSource, addressDestination, amount);

            HttpEntity<Transaction> request = new HttpEntity<>(transaction);
            ResponseEntity<TransferResponse> response =
                    restTemplate.exchange(url,
                            HttpMethod.POST,
                            request,
                            TransferResponse.class);

            if(response.getStatusCodeValue() != 200) {
                throw new Exception(response.toString());
            }
        } catch(Exception ex) {
            throw new Exception("Failed to send coins:" + ex.getMessage());
        }
    }

    private String buildAddressHistoryURL(String address) {
        return JOBCOIN_URL + ADDRESSES_ENDPOINT + address;
    }

    private String buildTransactionsUrl() {
        return JOBCOIN_URL + TRANSACTIONS_ENDPOINT;
    }

}

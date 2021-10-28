package com.gemini.jobcoin.services.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @NoArgsConstructor @ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class History {
    @JsonProperty("balance")
    private String balance;
    @JsonProperty("transactions")
    private List<Transaction> transactions;

    public History(String balance, List<Transaction> transactions) {
        this.balance = balance;
        this.transactions = transactions;
    }
}

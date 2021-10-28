package com.gemini.jobcoin.services.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("fromAddress")
    private String fromAddress;
    @JsonProperty("toAddress")
    private String toAddress;
    @JsonProperty("amount")
    private String amount;

    public Transaction(String timestamp, String fromAddress, String toAddress, String amount) {
        this.timestamp = timestamp;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.amount = amount;
    }
}

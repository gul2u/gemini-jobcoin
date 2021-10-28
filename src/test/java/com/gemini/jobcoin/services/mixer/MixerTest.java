package com.gemini.jobcoin.services.mixer;

import com.gemini.jobcoin.services.client.JobcoinHttpClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class MixerTest {

    @Mock
    private HashMap<String, List<String>> mixerAddresses;

    @Mock
    private JobcoinHttpClient jobcoinHttpClient;

    @Mock
    private AddressGenerator addressGenerator;

    @InjectMocks
    private Mixer mixer;

    @Test
    public void mix() {
        List<String> addresses = new ArrayList<>();
        addresses.add("12345");
        addresses.add("abcdef");
        addresses.add("xyz123");
        String jobcoinAddress = "jobcoin123";
        // TODO: Mock addressGenerator and mixerAddresses
        // TODO: Assert addressGenerator.generateDepositAddress() called once
        // TODO: Assert mixerAddresses.put() called once
        // TODO: Assert jobcoinAddress returned
    }

    @Test
    public void monitorTransactions() {
        // TODO: Mock jobcoinHttpClient.getAddressHistory()
        // TODO: Test for balance == null, balance == 0, balance > 0, balance < 1, balance == LARGE_NUMBER
    }

    @Test
    public void mixCoins() {
        // TODO: Mock jobcoinHttpClient.getAddressHistroy(), jobcoinHttpClient.sendCoins()
        // TODO: Implement MockServer to validate total coins sent per address
    }
}
package com.gemini.jobcoin.services.mixer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AddressGeneratorTest {

    @Test
    void generateDepositAddress() throws Exception{
        AddressGenerator addressGenerator = new AddressGenerator();
        String address = addressGenerator.generateDepositAddress();
        Assertions.assertEquals(6, address.length());
    }
}
package com.gemini.jobcoin.services.mixer;

import org.apache.tomcat.util.buf.HexUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;

public class AddressGenerator {
    public String generateDepositAddress() throws Exception {
        MessageDigest salt = MessageDigest.getInstance("SHA-256");
        salt.update(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
        return HexUtils.toHexString(salt.digest()).substring(0, 6);
    }
}

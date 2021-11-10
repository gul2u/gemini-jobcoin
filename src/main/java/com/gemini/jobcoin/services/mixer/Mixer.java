package com.gemini.jobcoin.services.mixer;

import com.gemini.jobcoin.services.client.JobcoinHttpClient;
import com.gemini.jobcoin.services.models.History;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.*;

// Mixer should handle mixing the coins and depositing to the input addresses
@EnableAsync
public class Mixer {

    private final JobcoinHttpClient jobcoinHttpClient;
    private final AddressGenerator addressGenerator;
    private final HashMap<String, List<String>> mixerAddresses;
    private final int distributionInterval = 10;    // Arbitrary number for mixer distribution amount

    public Mixer(JobcoinHttpClient jobcoinHttpClient, AddressGenerator addressGenerator, HashMap<String, List<String>> mixerAddresses) {
        this.jobcoinHttpClient = jobcoinHttpClient;
        this.addressGenerator = addressGenerator;
        this.mixerAddresses = mixerAddresses;
    }

    public String mix(List<String> addresses) throws Exception {
        System.out.printf("Mix coins into these addresses: %s\n", addresses);
        String jobcoinAddress = addressGenerator.generateDepositAddress();

        // Emulates persistence of Jobcoin Address to Deposit Addresses
        mixerAddresses.put(jobcoinAddress, addresses);

        return jobcoinAddress;
    }

    public void monitorTransactions() throws Exception {
        for(Map.Entry<String, List<String>> mixerEntry: mixerAddresses.entrySet()) {
            if(Float.parseFloat(getJobcoinBalance(mixerEntry.getKey()).getBalance()) > 0) {
                mixCoins(mixerEntry.getKey(), mixerEntry.getValue());
            }
        }
    }

    public History getJobcoinBalance(String jobcoinAddress) throws Exception {
        History history = jobcoinHttpClient.getAddressHistory(jobcoinAddress);
        System.out.println(history.toString());
        return history;
    }

    @Async
    public void sendJobcoins(String addressSource, String addressDestination, String amount) throws Exception {
        jobcoinHttpClient.sendCoins(addressSource, addressDestination, amount);
    }

    // TODO: Implement more elegant distribution method
    public void mixCoinsDiscreet(String jobcoinAddress, List<String> addresses) throws Exception {
        History history = getJobcoinBalance(jobcoinAddress);
        float balanceRemaining = Float.parseFloat(history.getBalance());
        float depositAmount = balanceRemaining/addresses.size()/distributionInterval;
        while(balanceRemaining > 0) {
            for(String address: addresses) {
                balanceRemaining = Float.parseFloat(getJobcoinBalance(jobcoinAddress).getBalance());
                if(balanceRemaining >= depositAmount) {
                    sendJobcoins(jobcoinAddress, address, Float.toString(depositAmount));
                } else if(balanceRemaining > 0) {
                    sendJobcoins(jobcoinAddress, address, Float.toString(balanceRemaining));
                }
            }
            balanceRemaining = Float.parseFloat(getJobcoinBalance(jobcoinAddress).getBalance());
        }
    }

    public void mixCoins(String jobcoinAddress, List<String> addresses) throws Exception {
        float balanceRemaining = Float.parseFloat(getJobcoinBalance(jobcoinAddress).getBalance());
        float depositAmount = (float)Math.floor(balanceRemaining/addresses.size());
        float remaining = balanceRemaining % addresses.size();

        for(String address: addresses) {
            sendJobcoins(jobcoinAddress, address, String.valueOf(depositAmount));
        }
        if(remaining > 0 && addresses.size() > 0) {
            sendJobcoins(jobcoinAddress, addresses.get(addresses.size() - 1), String.valueOf(remaining));
        }
    }

    @SneakyThrows
    public String debugMixer(int numAddresses) {
        List<String> addresses = new ArrayList<>();
        for(int i = 0; i < numAddresses; i++) {
            addresses.add(addressGenerator.generateDepositAddress());
        }
        return mix(addresses);
    }

}

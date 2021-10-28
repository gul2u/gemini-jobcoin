package com.gemini.jobcoin.services;

import com.gemini.jobcoin.services.mixer.Mixer;
import com.gemini.jobcoin.services.mixer.MixerScannerTask;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;

@SpringBootApplication
public class GeminiJobcoinApplication implements CommandLineRunner {

    private final Scanner scanner = new Scanner(System.in);
    private final Mixer mixer;

    private static final long mixerScanPeriod = 15000;
    private static final boolean debug = false;

    public GeminiJobcoinApplication(Mixer mixer) {
        this.mixer = mixer;
    }

    public static void main(String[] args) { SpringApplication.run(GeminiJobcoinApplication.class, args); }

    @Override
    public void run(String[] args) {
        Timer timer = new Timer();
        MixerScannerTask mixerScannerTask = new MixerScannerTask(mixer);
        timer.scheduleAtFixedRate(mixerScannerTask, 0, mixerScanPeriod);
        boolean cont = false;
        do {
            if (debug) {
                System.out.println("[DEBUG] Deposit addresses to generate:");
                System.out.println("Jobcoin Address: " + mixer.debugMixer(Integer.parseInt(scanner.nextLine())));
            } else {

                try {
                    List<String> addresses = inputDepositAddresses();
                    String jobcoinAddress = mixer.mix(addresses);

                    System.out.printf("You may now send Jobcoins to address: %s\n", jobcoinAddress);
                    System.out.printf("They will be mixed and distributed to your destination addresses: %s.\n", addresses);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                System.out.println("Continue(y/n)?");
                cont = !scanner.nextLine().replaceAll("\\s", "").equalsIgnoreCase("n");
            }
        } while(cont) ;
    }

    public List<String> inputDepositAddresses() {
        System.out.println("Please enter a comma-separated list of new, unused Jobcoin addresses where your mixed Jobcoins will be sent:");
        List<String> depositAddresses = Arrays.asList(scanner.nextLine().replaceAll("\\s", "").split(","));
        // TODO: Validate addresses
        return depositAddresses;
    }

    public static boolean validateNewAddress(String address) {
        //TODO: Validate address format
        //TODO: Validate new/unused address
        return true;
    }

}


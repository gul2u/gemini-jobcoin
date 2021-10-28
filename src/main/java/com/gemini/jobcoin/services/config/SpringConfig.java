package com.gemini.jobcoin.services.config;

import com.gemini.jobcoin.services.client.JobcoinHttpClient;
import com.gemini.jobcoin.services.mixer.AddressGenerator;
import com.gemini.jobcoin.services.mixer.Mixer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@Configuration
@EnableAutoConfiguration
public class SpringConfig {

    @Bean
    public RestTemplate restTemplate() { return new RestTemplate(); }

    @Bean
    public AddressGenerator addressGenerator() {
        return new AddressGenerator();
    }

    @Bean
    public JobcoinHttpClient jobcoinHttpClient() {
        return new JobcoinHttpClient(restTemplate());
    }

    @Bean
    public HashMap<String, List<String>> mixerAddresses() { return new HashMap<>(); }

    @Bean
    public Mixer mixer() {
        return new Mixer(jobcoinHttpClient(), addressGenerator(), mixerAddresses());
    }

}

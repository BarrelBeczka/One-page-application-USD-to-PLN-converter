package com.barrelbeczka.converter.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Slf4j
public class CurrencyService {

    private double usdRate;
    private static final String NBP_API_URL = "http://api.nbp.pl/api/exchangerates/rates/a/usd/?format=json";

    @PostConstruct
    public void init() {
        fetchRate();
    }

    private void fetchRate() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(NBP_API_URL, String.class);
            
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(result);
            this.usdRate = root.path("rates").get(0).path("mid").asDouble();
            
            log.info("Fetched USD rate: {}", this.usdRate);
        } catch (Exception e) {
            log.error("Failed to fetch USD rate, using default 4.0", e);
            this.usdRate = 4.0; // Fallback
        }
    }

    public double getRate() {
        return this.usdRate;
    }
}

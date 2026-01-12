package com.barrelbeczka.converter.service;


import com.barrelbeczka.converter.model.ExchangeRate;
import com.barrelbeczka.converter.repository.ExchangeRateRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyService {

    private final ExchangeRateRepository exchangeRateRepository;

    public double getRate() {
        return exchangeRateRepository.findById("USD")
                .map(ExchangeRate::getRate)
                .orElseGet(() -> {
                    log.warn("USD rate not found in DB, returning default 4.0");
                    return 4.0;
                });
    }
}

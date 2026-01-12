package com.barrelbeczka.converter.controller;

import com.barrelbeczka.converter.model.ConversionRecord;
import com.barrelbeczka.converter.repository.ConversionRepository;
import com.barrelbeczka.converter.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ConversionController {

    private final CurrencyService currencyService;
    private final ConversionRepository conversionRepository;

    @PostMapping("/convert")
    public ConversionRecord convert(@RequestParam double amount, @RequestParam(defaultValue = "PLN") String sourceCurrency) {
        double rate = currencyService.getRate();
        double targetAmount;
        String targetCurrency;

        if ("USD".equalsIgnoreCase(sourceCurrency)) {
            // USD -> PLN
            targetAmount = amount * rate;
            targetCurrency = "PLN";
        } else {
            // PLN -> USD
            targetAmount = amount / rate;
            targetCurrency = "USD";
        }
        
        // Round to 2 decimal places
        targetAmount = Math.round(targetAmount * 100.0) / 100.0;

        ConversionRecord record = new ConversionRecord(amount, targetAmount, sourceCurrency.toUpperCase(), targetCurrency, rate, LocalDateTime.now());
        return conversionRepository.save(record);
    }

    @GetMapping("/history")
    public List<ConversionRecord> getHistory() {
        // Return latest first
        // Ideally we would use pagination or a custom query method, but doing it in memory for now to match previous logic exactly or simply sort the findAll result.
        // Better yet: let's do it in Java for simplicity as requested plan implies simple replacement.
        return conversionRepository.findAll().stream()
                .sorted(Comparator.comparing(ConversionRecord::getTimestamp).reversed())
                .collect(Collectors.toList());
    }

    @GetMapping("/history/filter")
    public List<ConversionRecord> getFilteredHistory(@RequestParam String type) {
        // type: "max" for largest conversions (by source amount), "min" for smallest
        Comparator<ConversionRecord> comparator = Comparator.comparing(ConversionRecord::getSourceAmount);
        
        if ("max".equalsIgnoreCase(type)) {
            return conversionRepository.findAll().stream()
                    .sorted(comparator.reversed())
                    .limit(5)
                    .collect(Collectors.toList());
        } else if ("min".equalsIgnoreCase(type)) {
            return conversionRepository.findAll().stream()
                    .sorted(comparator)
                    .limit(5)
                    .collect(Collectors.toList());
        }
        
        return getHistory();
    }
}

package com.barrelbeczka.converter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversionRecord {
    private double sourceAmount;
    private double targetAmount;
    private String sourceCurrency;
    private String targetCurrency;
    private double rateUsed;
    private LocalDateTime timestamp;
}

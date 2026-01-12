package com.barrelbeczka.converter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversionRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private double sourceAmount;
    private double targetAmount;
    private String sourceCurrency;
    private String targetCurrency;
    private double rateUsed;
    private LocalDateTime timestamp;

    public ConversionRecord(double sourceAmount, double targetAmount, String sourceCurrency, String targetCurrency, double rateUsed, LocalDateTime timestamp) {
        this.sourceAmount = sourceAmount;
        this.targetAmount = targetAmount;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.rateUsed = rateUsed;
        this.timestamp = timestamp;
    }
}

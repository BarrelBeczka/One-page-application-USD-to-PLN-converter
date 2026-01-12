package com.barrelbeczka.converter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRate {

    @Id
    private String currencyCode; // e.g., "USD"
    
    private double rate; // e.g., 4.0
}

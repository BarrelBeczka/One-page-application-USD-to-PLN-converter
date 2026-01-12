package com.barrelbeczka.converter.repository;

import com.barrelbeczka.converter.model.ConversionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversionRepository extends JpaRepository<ConversionRecord, Long> {
}

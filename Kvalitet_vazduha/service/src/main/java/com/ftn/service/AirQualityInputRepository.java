package com.ftn.service;

import com.ftn.model.AirQualityInput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirQualityInputRepository extends JpaRepository<AirQualityInput, Long> {
}
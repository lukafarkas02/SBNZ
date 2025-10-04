package com.ftn.service;

import com.ftn.model.AirQualityInput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AirQualityInputRepository extends JpaRepository<AirQualityInput, Long> {
    @Query("SELECT a FROM AirQualityInput a WHERE a.timestamp >= :startTime ORDER BY a.timestamp ASC")
    List<AirQualityInput> findAllFromLast24h(@Param("startTime") LocalDateTime startTime);
}
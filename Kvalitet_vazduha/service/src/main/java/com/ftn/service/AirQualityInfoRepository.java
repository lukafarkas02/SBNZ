package com.ftn.service;

import com.ftn.model.AirQualityInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirQualityInfoRepository extends JpaRepository<AirQualityInfo, Long> {
}
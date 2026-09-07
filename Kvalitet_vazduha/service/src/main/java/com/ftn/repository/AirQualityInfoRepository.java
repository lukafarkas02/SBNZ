package com.ftn.repository;

import com.ftn.model.AirQualityInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AirQualityInfoRepository extends JpaRepository<AirQualityInfo, Long> {

    Optional<AirQualityInfo>
    findTopByInput_User_EmailOrderByInput_TimestampDesc(
            String email
    );
}
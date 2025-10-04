package com.ftn.service;

import com.ftn.dto.PollutantHistoryDTO;
import com.ftn.model.PollutantType;
import com.ftn.model.AirQualityInput;
import com.ftn.model.Pollutant;
import com.ftn.service.AirQualityService;

import java.util.List;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import lombok.*;

@Service
@RequiredArgsConstructor
public class AirQualityService {

    private final AirQualityInputRepository airQualityInputRepository;

    public List<PollutantHistoryDTO> getPollutantHistoryLast24h() {
        LocalDateTime startTime = LocalDateTime.now().minusHours(24);
        List<AirQualityInput> inputs = airQualityInputRepository.findAllFromLast24h(startTime);

        return inputs.stream().map(input -> {
            System.out.println(input);
            double pm25 = input.getPollutantMeasurment().getPollutants().stream()
                    .filter(p -> p.getPollutantType() == PollutantType.PM2_5)
                    .mapToDouble(Pollutant::getValue)
                    .findFirst()
                    .orElse(0.0);

            double pm10 = input.getPollutantMeasurment().getPollutants().stream()
                    .filter(p -> p.getPollutantType() == PollutantType.PM10)
                    .mapToDouble(Pollutant::getValue)
                    .findFirst()
                    .orElse(0.0);

            return new PollutantHistoryDTO(input.getTimestamp(), pm25, pm10);
        }).collect(Collectors.toList());
    }
}

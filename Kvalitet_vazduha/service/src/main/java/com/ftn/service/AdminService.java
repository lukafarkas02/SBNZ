package com.ftn.service;

import com.ftn.dto.AdminMeasurementDTO;
import com.ftn.dto.AdminPollutantDTO;
import com.ftn.dto.AdminWarningDTO;
import com.ftn.model.AirQualityInput;
import com.ftn.model.Pollutant;
import com.ftn.model.User;
import com.ftn.model.messages.Warning;
import com.ftn.repository.AirQualityInputRepository;
import com.ftn.repository.WarningRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final AirQualityInputRepository airQualityInputRepository;
    private final WarningRepository warningRepository;

    public AdminService(
            AirQualityInputRepository airQualityInputRepository,
            WarningRepository warningRepository
    ) {
        this.airQualityInputRepository = airQualityInputRepository;
        this.warningRepository = warningRepository;
    }

    public List<AdminMeasurementDTO> getAllMeasurements() {

        return airQualityInputRepository.findAll()
                .stream()
                .map(this::mapMeasurementToDTO)
                .collect(Collectors.toList());
    }

    public List<AdminWarningDTO> getAllWarnings() {

        return warningRepository.findAll()
                .stream()
                .map(this::mapWarningToDTO)
                .collect(Collectors.toList());
    }

    private AdminMeasurementDTO mapMeasurementToDTO(
            AirQualityInput input
    ) {

        User user = input.getUser();

        List<AdminPollutantDTO> pollutants =
                input.getPollutantMeasurment() != null &&
                input.getPollutantMeasurment().getPollutants() != null

                        ? input.getPollutantMeasurment()
                        .getPollutants()
                        .stream()
                        .map(this::mapPollutantToDTO)
                        .collect(Collectors.toList())

                        : List.of();

        return new AdminMeasurementDTO(
                input.getId(),

                user != null ? user.getFirstName() : null,
                user != null ? user.getLastName() : null,
                user != null ? user.getEmail() : null,
                user != null ? user.getLocation() : null,

                input.getTimestamp(),

                pollutants
        );
    }

    private AdminPollutantDTO mapPollutantToDTO(
            Pollutant pollutant
    ) {

        return new AdminPollutantDTO(
                pollutant.getPollutantType(),
                pollutant.getValue(),
                pollutant.getStatus()
        );
    }

    private AdminWarningDTO mapWarningToDTO(
            Warning warning
    ) {

        User user = warning.getUser();

        return new AdminWarningDTO(
                warning.getId(),

                warning.getType(),
                warning.getContent(),
                warning.getTimestamp(),

                user != null ? user.getFirstName() : null,
                user != null ? user.getLastName() : null,
                user != null ? user.getEmail() : null,
                user != null ? user.getLocation() : null
        );
    }
}
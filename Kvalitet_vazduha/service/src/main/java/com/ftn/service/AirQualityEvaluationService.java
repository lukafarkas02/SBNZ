package com.ftn.service;

import com.ftn.dto.AirQualityEvaluationResponseDTO;
import com.ftn.dto.AirQualityRequestDTO;
import com.ftn.model.AirQualityInfo;
import com.ftn.model.AirQualityInput;
import com.ftn.model.HealthCheckReminder;
import com.ftn.model.Measurement;
import com.ftn.model.OutdoorActivityPlan;
import com.ftn.model.Pollutant;
import com.ftn.model.SmartHomeCommand;
import com.ftn.model.User;
import com.ftn.model.WeatherConditions;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import com.ftn.repository.UserRepository;
import com.ftn.repository.AirQualityInfoRepository;
import com.ftn.repository.AirQualityInputRepository;

@Service
public class AirQualityEvaluationService {

    private static final String FORWARD_SESSION_NAME = "basic-ksession";

    private final AirQualityInfoRepository airQualityInfoRepository;
    private final UserRepository userRepository;
    private final AirQualityInputRepository airQualityInputRepository;
    private final KieContainer kieContainer;

    public AirQualityEvaluationService(
            AirQualityInfoRepository airQualityInfoRepository,
            UserRepository userRepository,
            AirQualityInputRepository airQualityInputRepository,
            KieContainer kieContainer
    ) {
        this.airQualityInfoRepository = airQualityInfoRepository;
        this.userRepository = userRepository;
        this.airQualityInputRepository = airQualityInputRepository;
        this.kieContainer = kieContainer;
    }

    public AirQualityEvaluationResponseDTO evaluate(
            AirQualityRequestDTO request
    ) {
        validateRequest(request);

        System.out.println("REQUEST >> " + request);

        User user = findUser(request.getEmail());
        Measurement measurement = prepareMeasurement(request.getMeasurement());

        AirQualityInput input = createAndSaveInput(
                user,
                measurement
        );

        WeatherConditions weather = request.getWeather();

        return executeForwardRules(input, weather);
    }

    private void validateRequest(AirQualityRequestDTO request) {
        if (request == null) {
            throw new IllegalArgumentException(
                    "Request must not be null."
            );
        }

        if (request.getEmail() == null ||
                request.getEmail().isBlank()) {

            throw new IllegalArgumentException(
                    "Email must not be empty."
            );
        }

        if (request.getMeasurement() == null) {
            throw new IllegalArgumentException(
                    "Measurement must not be null."
            );
        }
    }

    private User findUser(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with email: " + email
                        )
                );
    }

    private Measurement prepareMeasurement(
            Measurement measurement
    ) {
        if (measurement.getPollutants() != null) {
            for (Pollutant pollutant : measurement.getPollutants()) {
                pollutant.setMeasurement(measurement);
            }
        }

        return measurement;
    }

    private AirQualityInput createAndSaveInput(
            User user,
            Measurement measurement
    ) {
        AirQualityInput input = new AirQualityInput();

        input.setUser(user);
        input.setPollutantMeasurment(measurement);
        input.setTimestamp(LocalDateTime.now());

        return airQualityInputRepository.save(input);
    }

    private AirQualityEvaluationResponseDTO executeForwardRules(
            AirQualityInput input,
            WeatherConditions weather
    ) {
        KieSession kieSession = null;

        try {
            kieSession = kieContainer.newKieSession(
                    FORWARD_SESSION_NAME
            );

            insertFacts(
                    kieSession,
                    input,
                    weather
            );

            int firedRules = kieSession.fireAllRules();

            System.out.println(
                    "Broj opaljenih forward pravila: " + firedRules
            );

            ForwardRuleResults results =
                    extractResults(kieSession);

            saveAirQualityInfo(results);

            AirQualityEvaluationResponseDTO response =
                    createResponse(results, weather);

            System.out.println("RESPONSE >> " + response);

            return response;

        } finally {
            if (kieSession != null) {
                kieSession.dispose();
            }
        }
    }

    private void insertFacts(
            KieSession kieSession,
            AirQualityInput input,
            WeatherConditions weather
    ) {
        kieSession.insert(input);

        if (weather != null) {
            kieSession.insert(weather);
        }
    }

    private ForwardRuleResults extractResults(
            KieSession kieSession
    ) {
        ForwardRuleResults results =
                new ForwardRuleResults();

        for (Object object : kieSession.getObjects()) {
            System.out.println(
                    "DROOLS OBJECT: " +
                            object.getClass().getName() +
                            " -> " +
                            object
            );

            if (object instanceof AirQualityInfo) {
                results.airQualityInfo =
                        (AirQualityInfo) object;

            } else if (object instanceof OutdoorActivityPlan) {
                results.outdoorActivityPlan =
                        (OutdoorActivityPlan) object;

            } else if (object instanceof HealthCheckReminder) {
                results.healthCheckReminder =
                        (HealthCheckReminder) object;

            } else if (object instanceof SmartHomeCommand) {
                results.smartHomeCommand =
                        (SmartHomeCommand) object;

            } else if (object instanceof WeatherConditions) {
                results.weatherConditions =
                        (WeatherConditions) object;
            }
        }

        return results;
    }

    private void saveAirQualityInfo(
            ForwardRuleResults results
    ) {
        if (results.airQualityInfo != null) {
            results.airQualityInfo =
                    airQualityInfoRepository.save(
                            results.airQualityInfo
                    );
        }
    }

    private AirQualityEvaluationResponseDTO createResponse(
            ForwardRuleResults results,
            WeatherConditions originalWeather
    ) {
        AirQualityEvaluationResponseDTO response =
                new AirQualityEvaluationResponseDTO();

        response.setAirQualityInfo(
                results.airQualityInfo
        );

        response.setOutdoorActivityPlan(
                results.outdoorActivityPlan
        );

        response.setHealthCheckReminder(
                results.healthCheckReminder
        );

        response.setSmartHomeCommand(
                results.smartHomeCommand
        );

        response.setWeatherConditions(
                results.weatherConditions != null
                        ? results.weatherConditions
                        : originalWeather
        );

        return response;
    }

    /*
     * Interna pomoćna klasa za rezultate iz Drools working memory.
     * Nije DTO i ne šalje se frontend-u.
     */
    private static class ForwardRuleResults {

        private AirQualityInfo airQualityInfo;
        private OutdoorActivityPlan outdoorActivityPlan;
        private HealthCheckReminder healthCheckReminder;
        private SmartHomeCommand smartHomeCommand;
        private WeatherConditions weatherConditions;
    }
}
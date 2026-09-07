package com.ftn.service;

import com.ftn.dto.ActivityDiagnosisResponse;
import com.ftn.dto.BackwardActivityRequest;

import com.ftn.model.AirQualityInfo;
import com.ftn.model.WeatherConditions;

import com.ftn.repository.AirQualityInfoRepository;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import org.springframework.stereotype.Service;

@Service
public class BackwardActivityService {

    private static final String SESSION_NAME =
            "backward-activity-ksession";

    private final KieContainer kieContainer;
    private final AirQualityInfoRepository airQualityInfoRepository;

    public BackwardActivityService(
            KieContainer kieContainer,
            AirQualityInfoRepository airQualityInfoRepository
    ) {
        this.kieContainer = kieContainer;
        this.airQualityInfoRepository = airQualityInfoRepository;
    }

    public ActivityDiagnosisResponse diagnose(
            BackwardActivityRequest request
    ) {
        validateRequest(request);

        /*
         * Učitavamo poslednji AirQualityInfo koji je napravljen
         * i sačuvan tokom forward evaluacije.
         */
        AirQualityInfo airQualityInfo =
                airQualityInfoRepository
                        .findTopByInput_User_EmailOrderByInput_TimestampDesc(
                                request.getUserEmail()
                        )
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "No previous air-quality evaluation found for user: "
                                                + request.getUserEmail()
                                )
                        );

        /*
         * WeatherConditions ostaje posebna Drools činjenica.
         */
        WeatherConditions weather = new WeatherConditions();
        weather.setWindSpeed(request.getWindSpeed());
        weather.setHumidity(request.getHumidity());
        weather.setTemperature(request.getTemperature());

        /*
         * Prazan response koji će Drools pravila popuniti.
         */
        ActivityDiagnosisResponse response =
                new ActivityDiagnosisResponse();

        KieSession kieSession =
                kieContainer.newKieSession(SESSION_NAME);

        try {
            kieSession.insert(airQualityInfo);
            kieSession.insert(weather);

            /*
             * Direktno ubacujemo HTTP request kao Drools činjenicu.
             * Više nema ActivityDiagnosisRequest.
             */
            kieSession.insert(request);

            kieSession.insert(response);

            int firedRules = kieSession.fireAllRules();

            System.out.println(
                    "Backward activity rules fired: " + firedRules
            );

            return response;

        } finally {
            kieSession.dispose();
        }
    }

    private void validateRequest(
            BackwardActivityRequest request
    ) {
        if (request == null) {
            throw new IllegalArgumentException(
                    "Request must not be null."
            );
        }

        if (request.getUserEmail() == null ||
                request.getUserEmail().isBlank()) {

            throw new IllegalArgumentException(
                    "User email must be provided."
            );
        }

        /*
         * ActivityType je enum, zato proveravamo samo null.
         */
        if (request.getActivityType() == null) {
            throw new IllegalArgumentException(
                    "Activity type must be provided."
            );
        }

        if (request.getDurationMinutes() <= 0) {
            throw new IllegalArgumentException(
                    "Duration must be greater than zero."
            );
        }

        if (request.getHumidity() < 0 ||
                request.getHumidity() > 100) {

            throw new IllegalArgumentException(
                    "Humidity must be between 0 and 100."
            );
        }
    }
}
package com.ftn.model;

import javax.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "decision_explanations")
@Data
public class DecisionExplanations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "influencing_pollutants", joinColumns = @JoinColumn(name = "decision_explanation_id"))
    @Column(name = "pollutant")
    private List<String> influencingPollutants;

    @ElementCollection
    @CollectionTable(name = "weather_factors", joinColumns = @JoinColumn(name = "decision_explanation_id"))
    @Column(name = "factor")
    private List<String> weatherFactors;

    @ElementCollection
    @CollectionTable(name = "context_factors", joinColumns = @JoinColumn(name = "decision_explanation_id"))
    @Column(name = "factor")
    private List<String> contextFactors;
    
    private String reasoning;
}
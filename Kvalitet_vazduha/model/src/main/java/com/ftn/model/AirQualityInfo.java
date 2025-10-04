package com.ftn.model;

import javax.persistence.*;
import lombok.*;

import com.ftn.model.AirQualityCategory;
import com.ftn.model.DecisionExplanations;
import com.ftn.model.messages.Message;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "air_quality_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirQualityInfo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AirQualityCategory airCategory;

    @OneToOne(cascade = CascadeType.ALL)
    private DecisionExplanations explanation;

    @OneToOne(cascade = CascadeType.ALL)
    private Message recommendation;

    @ElementCollection
    @CollectionTable(name = "air_quality_warnings", joinColumns = @JoinColumn(name = "air_quality_info_id"))
    @Column(name = "warning")
    private List<String> warnings;

    @OneToOne
    @JoinColumn(name = "input_id") 
    private AirQualityInput input;
}
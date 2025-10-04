package com.ftn.model;

import javax.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "measurements")
// @Data
// @NoArgsConstructor
@AllArgsConstructor
@Builder
public class Measurement{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "measurement")
    private List<Pollutant> pollutants;

    // Default konstruktor
    public Measurement() {}

    // Getter
    public List<Pollutant> getPollutants() {
        return pollutants;
    }

    // Setter
    public void setPollutants(List<Pollutant> pollutants) {
        this.pollutants = pollutants;
    }
}
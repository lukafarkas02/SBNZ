package com.ftn.dto;

import com.ftn.model.Measurement;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirQualityRequestDTO {
    private Measurement measurement;
    private String email;
}

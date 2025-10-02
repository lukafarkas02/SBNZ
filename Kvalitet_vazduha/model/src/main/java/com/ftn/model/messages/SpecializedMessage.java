package com.ftn.model.messages;

import com.ftn.model.RiskType;
import lombok.Data;

@Data
public class SpecializedMessage extends Message {
    private RiskType targetGroup; 
    // npr. DETE, STARIJA_OSOBA, HRONICNI_BOLESNIK, TRUDNICA
}

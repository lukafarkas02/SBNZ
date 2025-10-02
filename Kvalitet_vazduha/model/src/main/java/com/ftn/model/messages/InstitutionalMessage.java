package com.ftn.model.messages;

import lombok.Data;

@Data
public class InstitutionalMessage extends Message {
    private String institutionType; // Škola, vrtić, bolnica...
    private String recommendation;  // npr. "Prekinuti aktivnosti na otvorenom"
}

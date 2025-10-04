package com.ftn.model.messages;

import javax.persistence.*;
import lombok.*;

import com.ftn.model.InstitutionType;

import java.util.List;
import java.util.Map;

@Entity
@Data
public class InstitutionalMessage extends Message {
    @Lob
    @Column(columnDefinition = "TEXT")
    private String recommendation;
}

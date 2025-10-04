package com.ftn.model.messages;

import javax.persistence.*;
import lombok.*;

import com.ftn.model.RiskType;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Entity
@Data
public class SpecializedMessage extends Message {
    @Lob
    @Column(columnDefinition = "TEXT")
    private String recommendation;
}
